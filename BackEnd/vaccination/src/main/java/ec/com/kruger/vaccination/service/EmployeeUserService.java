package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.api.dto.UserRq;
import ec.com.kruger.vaccination.exception.CredentialInvalidException;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.model.EmployeeUser;
import ec.com.kruger.vaccination.repository.EmployeeUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeUserService {

    private final EmployeeUserRepository userRepo;

    @Value("${secretKey}")
    private String secret;

    public EmployeeUserService(EmployeeUserRepository employeeUserRepository){
        this.userRepo = employeeUserRepository;
    }

    public UserRq login(String username, String pwd)
            throws DocumentNotFoundException, CredentialInvalidException {
        EmployeeUser user = userRepo.findByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(pwd)) {
                String token = getJwtToken(user);
                return UserRq.builder().user(username).token(token).role(user.getRole()).build();
            } else {
                throw new CredentialInvalidException("Incorrect credentials.");
            }
        } else {
            throw new DocumentNotFoundException("This user does not exist." + username);
        }
    }

    private String getJwtToken(EmployeeUser user) {
        String secretKey = "Kruger";
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        String token = Jwts.builder().setId("softtekJWT").setSubject(user.getUsername())
                .claim("authorities",
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}

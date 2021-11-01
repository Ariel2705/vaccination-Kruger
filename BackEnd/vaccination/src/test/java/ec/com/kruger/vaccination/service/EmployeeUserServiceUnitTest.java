package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.api.dto.LoginRq;
import ec.com.kruger.vaccination.api.dto.UserRq;
import ec.com.kruger.vaccination.exception.CredentialInvalidException;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.model.EmployeeUser;
import ec.com.kruger.vaccination.repository.EmployeeUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeUserServiceUnitTest {

    @Mock
    private EmployeeUserRepository repository;
    @InjectMocks
    private EmployeeUserService service;
    @InjectMocks
    private UserRq userRq;
    @InjectMocks
    private LoginRq loginRq;

    @BeforeEach
    public void setUp() {
        userRq = new UserRq();
        loginRq = new LoginRq();
    }

    @Test
    public void givenUserAndPwdReturnUserRq() {
        try {
            EmployeeUser user = new EmployeeUser();
            userRq.setUser("testUser");
            userRq.setRole("testRole");
            userRq.setToken("testToken");
            loginRq.setUsername(userRq.getUser());
            loginRq.setPwd(userRq.getRole());
            user.setPassword(loginRq.getPwd());
            when(repository.findByUsername(any()))
                    .thenReturn(user);
            service.login(loginRq.getUsername(), loginRq.getPwd());
            Assertions.assertEquals(userRq, service.login(loginRq.getUsername(), loginRq.getPwd()));
        } catch (DocumentNotFoundException | CredentialInvalidException ex) {
            Logger.getLogger(EmployeeUserServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(EmployeeUserServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, e);
        }
    }

}

package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.api.EmployeeUserController;
import ec.com.kruger.vaccination.api.dto.LoginRq;
import ec.com.kruger.vaccination.api.dto.UserRq;
import ec.com.kruger.vaccination.exception.CredentialInvalidException;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.model.EmployeeUser;
import ec.com.kruger.vaccination.model.Parish;
import ec.com.kruger.vaccination.repository.EmployeeUserRepository;
import ec.com.kruger.vaccination.repository.ParishRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeUserServiceUnitTest {

    @Mock
    private EmployeeUserRepository repository;

    @InjectMocks
    private EmployeeUserService service;
    private EmployeeUser employeeUser;

    @BeforeEach
    public void setUp() {
        employeeUser = new EmployeeUser();
    }

    @Test
    public void givenUserAndPwdReturnUserRq() {
        EmployeeUserController controller = new EmployeeUserController(service);
        UserRq userRq = new UserRq();
        LoginRq loginRq = new LoginRq();
        loginRq.setUsername("1804915617");
        loginRq.setPwd("Kruger123");
        try {
            Assertions.assertEquals(userRq, service.login(loginRq.getUsername(), loginRq.getPwd()));
        } catch (DocumentNotFoundException | CredentialInvalidException ex) {
            Logger.getLogger(EmployeeUserServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void givenUserAndPwdIncorrectThrowException() {
        String username = "testUser";
        String pwd = "testPwd";
        Assertions.assertThrows(DocumentNotFoundException.class, () -> service.login(username, pwd));
    }

}

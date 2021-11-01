package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.api.dto.LoginRq;
import ec.com.kruger.vaccination.api.dto.UserRq;
import ec.com.kruger.vaccination.exception.CredentialInvalidException;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Level;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class EmployeeUserServiceUnitTest {

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
            Assertions.assertEquals(userRq, service.login(loginRq.getUsername(), loginRq.getPwd()));
        } catch (DocumentNotFoundException | CredentialInvalidException ex) {
            Logger.getLogger(EmployeeUserServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(EmployeeUserServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, e);
        }

    }

}

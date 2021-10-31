package ec.com.kruger.vaccination.api;

import ec.com.kruger.vaccination.api.dto.LoginRq;
import ec.com.kruger.vaccination.exception.CredentialInvalidException;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.model.EmployeeUser;
import ec.com.kruger.vaccination.service.EmployeeUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class EmployeeUserControllerUnitTest {

    @Mock
    private EmployeeUserService service;

    @InjectMocks
    EmployeeUser employeeUser;

    @BeforeEach
    public void setUp() {
        employeeUser = new EmployeeUser();
    }

    @Test
    public void givenLoginRqReturnOk() {
        EmployeeUserController controller = new EmployeeUserController(service);
        LoginRq loginRq = new LoginRq();
        ResponseEntity response = ResponseEntity.ok().build();
        Assertions.assertEquals(response, controller.login(loginRq));
    }

    @Test
    public void givenBadLoginRqReturnBadRequest() {
        EmployeeUserController controller = new EmployeeUserController(service);
        LoginRq loginRq = new LoginRq();
        try {
            Mockito
                    .doThrow(DocumentNotFoundException.class)
                    .doThrow(CredentialInvalidException.class)
                    .when (service)
                    .login(employeeUser.getUsername(),employeeUser.getPassword());
        } catch (DocumentNotFoundException | CredentialInvalidException ex) {
            Logger.getLogger(EmployeeUserControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.notFound().build();
        ResponseEntity responseBad = ResponseEntity.badRequest().build();
        Assertions.assertEquals(response, controller.login(loginRq));
        Assertions.assertEquals(responseBad, controller.login(loginRq));
    }
}

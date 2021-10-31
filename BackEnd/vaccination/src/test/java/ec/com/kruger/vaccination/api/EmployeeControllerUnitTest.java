package ec.com.kruger.vaccination.api;

import ec.com.kruger.vaccination.api.dto.DetailByDateRq;
import ec.com.kruger.vaccination.api.dto.LoginRq;
import ec.com.kruger.vaccination.api.dto.UpdateEmployeeRq;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.exception.UpdateException;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.model.EmployeeUser;
import ec.com.kruger.vaccination.model.Parish;
import ec.com.kruger.vaccination.service.EmployeeService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerUnitTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
    }

    @Test
    public void givenEmployeeReturnOk() {
        EmployeeController controller = new EmployeeController(service);
        ResponseEntity response = ResponseEntity.ok().build();
        Assertions.assertEquals(response, controller.create(employee));
    }

    @Test
    public void givenBadEmployeeReturnBadRequest() {
        EmployeeController controller = new EmployeeController(service);
        try {
            Mockito.doThrow(InsertException.class)
                    .when(service)
                    .createEmployee(employee);
        } catch (InsertException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.badRequest().build();
        Assertions.assertEquals(response, controller.create(employee));
    }

    @Test
    public void givenUpdateEmployeeRqReturnOk() {
        EmployeeController controller = new EmployeeController(service);
        UpdateEmployeeRq updateEmployeeRq = new UpdateEmployeeRq();
        ResponseEntity response = ResponseEntity.ok().build();
        Assertions.assertEquals(response, controller.completeData(updateEmployeeRq));
    }

    @Test
    public void givenUpdateEmployeeRqReturnBadRequest() {
        EmployeeController controller = new EmployeeController(service);
        UpdateEmployeeRq updateEmployeeRq = new UpdateEmployeeRq();
        try {
            Mockito.doThrow(UpdateException.class)
                    .when(service)
                    .completeDataEmployee(updateEmployeeRq);
        } catch (UpdateException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.badRequest().build();
        Assertions.assertEquals(response, controller.completeData(updateEmployeeRq));
    }

    @Test
    public void givenChangeStateEmployeeReturnOk() {
        EmployeeController controller = new EmployeeController(service);
        ResponseEntity response = ResponseEntity.ok().build();
        Assertions.assertEquals(response, controller.changeStateEmployee("testIdentification"));
    }

    @Test
    public void givenChangeStateEmployeeReturnBadRequest() {
        EmployeeController controller = new EmployeeController(service);
        try {
            Mockito.doThrow(UpdateException.class)
                    .when(service)
                    .changeStateEmployee("testIdentification");
        } catch (UpdateException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.badRequest().build();
        Assertions.assertEquals(response, controller.changeStateEmployee("testIdentification"));
    }

    @Test
    public void givenIdentificationReturnEmployee() {
        String identification = "testIdentification";
        EmployeeController controller = new EmployeeController(service);
        try {
            when(service.findEmployeeByIdentification(identification))
                    .thenReturn(employee);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.ok(employee);
        Assertions.assertEquals(response, controller.findEmployeeById(identification));
    }

    @Test
    public void givenIdentificationReturnNotFound() {
        String identification = "testIdentification";
        EmployeeController controller = new EmployeeController(service);
        try {
            Mockito.doThrow(DocumentNotFoundException.class)
                    .when(service)
                    .findEmployeeByIdentification(identification);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity responseNotFound = ResponseEntity.notFound().build();
        Assertions.assertEquals(responseNotFound, controller.findEmployeeById(identification));
    }

    @Test
    public void givenStateReturnEmployees() {
        String state = "stateEmployee";
        EmployeeController controller = new EmployeeController(service);
        List<Employee> employees = new ArrayList<>();
        try {
            when(service.findEmployeeByStateVaccination(state))
                    .thenReturn(employees);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.ok(employees);
        Assertions.assertEquals(response, controller.findEmployeeByState(state));
    }

    @Test
    public void givenStateReturnNotFound() {
        String state = "stateEmployee";
        EmployeeController controller = new EmployeeController(service);
        try {
            Mockito.doThrow(DocumentNotFoundException.class)
                    .when(service)
                    .findEmployeeByStateVaccination(state);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity responseNotFound = ResponseEntity.notFound().build();
        Assertions.assertEquals(responseNotFound, controller.findEmployeeByState(state));
    }

    @Test
    public void givenTypeVaccineReturnEmployees() {
        String typeVaccine = "typeVaccineEmployee";
        EmployeeController controller = new EmployeeController(service);
        List<Optional<Employee>> employees = new ArrayList<>();
        try {
            lenient().when(service.findEmployeeByTypeVaccine(typeVaccine))
                    .thenReturn(employees);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.ok(employees);
        Assertions.assertEquals(response, controller.findEmployeeByState(typeVaccine));
    }

    @Test
    public void givenTypeVaccineReturnNotFound() {
        String typeVaccine = "typeVaccineEmployee";
        EmployeeController controller = new EmployeeController(service);
        try {
            Mockito.doThrow(DocumentNotFoundException.class)
                    .when(service)
                    .findEmployeeByTypeVaccine(typeVaccine);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity responseNotFound = ResponseEntity.notFound().build();
        Assertions.assertEquals(responseNotFound, controller.findEmployeeByTypeVaccine(typeVaccine));
    }

    @Test
    public void givenDatesReturnEmployees() {
        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = LocalDate.now();
        DetailByDateRq detailByDateRq = new DetailByDateRq();
        EmployeeController controller = new EmployeeController(service);
        List<Optional<Employee>> employees = new ArrayList<>();
        try {
            lenient().when(service.findEmployeeByDates(initialDate, finalDate))
                    .thenReturn(employees);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.ok(employees);
        Assertions.assertEquals(response, controller.findEmployeeByDates(detailByDateRq));
    }

    @Test
    public void givenDatesReturnNotFound() {
        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = LocalDate.now();
        DetailByDateRq detailByDateRq = new DetailByDateRq();
        EmployeeController controller = new EmployeeController(service);
        try {
            Mockito.doThrow(DocumentNotFoundException.class)
                    .when(service)
                    .findEmployeeByDates(initialDate, finalDate);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity responseNotFound = ResponseEntity.notFound().build();
        Assertions.assertEquals(responseNotFound, controller.findEmployeeByDates(detailByDateRq));
    }
}

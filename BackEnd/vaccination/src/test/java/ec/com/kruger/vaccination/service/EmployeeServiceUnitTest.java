package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.api.dto.CompleteDataEmployeeRq;
import ec.com.kruger.vaccination.api.dto.UpdateEmployeeRq;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.exception.UpdateException;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.model.EmployeeUser;
import ec.com.kruger.vaccination.repository.EmployeeRepository;
import ec.com.kruger.vaccination.repository.EmployeeUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository repository;
    @Mock
    private EmployeeUserRepository employeeUserRepository;
    @InjectMocks
    private EmployeeService service;

    @Test
    public void givenEmployeeCreateEmployee() {
        Employee employee = new Employee();
        EmployeeUser user = new EmployeeUser();
        employee.setIdentification("1804915617");
        employee.setNames("Test Names");
        employee.setSurnames("Test Surnames");
        employee.setEmail("test@mail.com");
        employee.setState("ADM");
        employee.setStateVaccination("SI");

        try {
            service.createEmployee(employee);
            verify(repository, times(1)).saveAndFlush(employee);
            verify(employeeUserRepository, times(1)).save(user);
        } catch (InsertException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenUpdateEmployeeRqCompleteEmployee() {
        LocalDate date = LocalDate.now();
        Employee employee = new Employee();
        CompleteDataEmployeeRq completeDataEmployeeRq = new CompleteDataEmployeeRq();
        completeDataEmployeeRq.setIdentification("1804915617");
        completeDataEmployeeRq.setBirthDate(date);
        completeDataEmployeeRq.setCodParish(1);
        completeDataEmployeeRq.setPhone("0999256438");
        completeDataEmployeeRq.setMainAddress("MainStreet");
        completeDataEmployeeRq.setSideStreet("SideStreet");

        try {
            when(repository.findByIdentification(any())).thenReturn(employee);
            service.completeDataEmployee(completeDataEmployeeRq);
            verify(repository, times(1)).save(employee);
        } catch (UpdateException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenStateVaccinationReturnEmployees() {
        String stateVaccination = "SI";
        String state = "ACT";
        List<Employee> employees = repository.findByStateVaccinationAndState(stateVaccination, state);
        try {
            Assertions.assertEquals(employees, service.findEmployeeByStateVaccination(stateVaccination));
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenTypeVaccineReturnEmployees() {
        String typeVaccine = "testVaccine";

        try {
            service.findEmployeeByTypeVaccine(typeVaccine);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenDatesReturnEmployees() {
        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = LocalDate.now();

        try {
            service.findEmployeeByDates(initialDate, finalDate);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenIdentificationReturnEmployee() {
        String identification = "testIdentification";
        Employee employee = this.repository.findByIdentification(identification);
        try {
            when(repository.findByIdentification(any())).thenReturn(employee);
            service.findEmployeeByIdentification(identification);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenIdentificationReturnEmployeeChangeState() {
        String identification = "testIdentification";
        Employee employee = repository.findByIdentification(identification);
        try {
            when(repository.findByIdentification(any())).thenReturn(employee);
            service.changeStateEmployee(identification);
            verify(repository, times(1)).save(employee);
        } catch (UpdateException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenNullIdentificationThrowDocumentNotFoundException() {
        Assertions.assertThrows(DocumentNotFoundException.class, () -> service.findEmployeeByIdentification(null));
    }

    @Test
    public void givenNullCodParishThrowUpdateException() {
        LocalDate date = LocalDate.now();
        CompleteDataEmployeeRq completeDataEmployeeRq = new CompleteDataEmployeeRq();
        completeDataEmployeeRq.setIdentification("1804915617");
        completeDataEmployeeRq.setBirthDate(date);
        completeDataEmployeeRq.setCodParish(null);
        completeDataEmployeeRq.setPhone("0999256438");
        completeDataEmployeeRq.setMainAddress("MainStreet");
        completeDataEmployeeRq.setSideStreet("SideStreet");
        Assertions.assertThrows(UpdateException.class, () -> service.completeDataEmployee(completeDataEmployeeRq));
    }

}

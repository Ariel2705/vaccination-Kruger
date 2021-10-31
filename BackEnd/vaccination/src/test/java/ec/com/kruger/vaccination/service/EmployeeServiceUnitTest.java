package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.api.EmployeeUserController;
import ec.com.kruger.vaccination.api.dto.LoginRq;
import ec.com.kruger.vaccination.api.dto.UpdateEmployeeRq;
import ec.com.kruger.vaccination.api.dto.UserRq;
import ec.com.kruger.vaccination.exception.CredentialInvalidException;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.exception.UpdateException;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.model.EmployeeUser;
import ec.com.kruger.vaccination.repository.AddressRepository;
import ec.com.kruger.vaccination.repository.EmployeeRepository;
import ec.com.kruger.vaccination.repository.EmployeeUserRepository;
import ec.com.kruger.vaccination.security.Authorization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private AddressRepository addressRepository;
    @Mock
    private EmployeeUserRepository employeeUserRepository;
    @Mock
    private Authorization authorizationRq;
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
        UpdateEmployeeRq updateEmployee = new UpdateEmployeeRq();
        updateEmployee.setIdentification("1804915617");
        updateEmployee.setBirthDate(date);
        updateEmployee.setCodParish(1);
        updateEmployee.setPhone("0999256438");
        updateEmployee.setMainAddress("MainStreet");
        updateEmployee.setSideStreet("SideStreet");

        try {
            when(repository.findByIdentification(any())).thenReturn(employee);
            service.completeDataEmployee(updateEmployee);
            verify(repository, times(1)).save(employee);
        } catch (UpdateException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenIdentificationReturnEmployee() {
        String identification = "1804915617";
        Employee employee = repository.findByIdentification(identification);
        try {
            Assertions.assertEquals(employee, service.findEmployeeByIdentification(identification));
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }



    @Test
    public void givenStateVaccinationReturnEmployees() {
        String stateVaccination = "SI";
        List<Employee> employees = repository.findByStateVaccination(stateVaccination);
        try {
            Assertions.assertEquals(employees, service.findEmployeeByStateVaccination(stateVaccination));
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenNullIdentificationThrowDocumentNotFoundException() {
        Assertions.assertThrows(DocumentNotFoundException.class, () -> service.findEmployeeByIdentification(null));
    }

    @Test
    public void givenNullCodParishThrowUpdateException() {
        LocalDate date = LocalDate.now();
        UpdateEmployeeRq updateEmployee = new UpdateEmployeeRq();
        updateEmployee.setIdentification("1804915617");
        updateEmployee.setBirthDate(date);
        updateEmployee.setCodParish(null);
        updateEmployee.setPhone("0999256438");
        updateEmployee.setMainAddress("MainStreet");
        updateEmployee.setSideStreet("SideStreet");
        Assertions.assertThrows(UpdateException.class, () -> service.completeDataEmployee(updateEmployee));
    }

}

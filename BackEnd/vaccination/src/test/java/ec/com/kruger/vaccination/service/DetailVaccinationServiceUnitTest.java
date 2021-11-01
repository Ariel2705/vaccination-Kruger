package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.model.DetailVaccination;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.repository.DetailVaccinationRepository;
import ec.com.kruger.vaccination.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetailVaccinationServiceUnitTest {

    @Mock
    private DetailVaccinationRepository repository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DetailVaccinationService service;

    @Test
    public void givenDetailAndIdentificationCreateDetail() {
        DetailVaccination detailVaccination = new DetailVaccination();
        String identification = "testIdentification";
        detailVaccination.setCodEmployee(1);
        detailVaccination.setVaccineDate(LocalDate.now());
        detailVaccination.setVaccineDoses(1);
        detailVaccination.setVaccineType("");
        Employee employee = new Employee();

        try {
            lenient().when(employeeRepository.findByIdentification(any())).thenReturn(employee);
            service.createDetail(detailVaccination, identification);
            verify(repository, times(1)).save(detailVaccination);
        } catch (InsertException ex) {
            Logger.getLogger(DetailVaccinationService.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DetailVaccinationService.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenCodeEmployeeReturnDetails() {
        Integer code = 1;
        DetailVaccination detail = repository.findByCodEmployee(code);
        try {
            Assertions.assertEquals(detail, service.findDetailByCodEmployee(code));
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(DetailVaccinationService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenTypeVaccineReturnEmployees() {
        String typeVaccine = "testVaccine";

        try {
            service.findDetailByTypeVaccine(typeVaccine);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(DetailVaccinationService.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DetailVaccinationService.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenDatesReturnEmployees() {
        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = LocalDate.now();

        try {
            service.findDetailByDates(initialDate, finalDate);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(DetailVaccinationService.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DetailVaccinationService.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }
}

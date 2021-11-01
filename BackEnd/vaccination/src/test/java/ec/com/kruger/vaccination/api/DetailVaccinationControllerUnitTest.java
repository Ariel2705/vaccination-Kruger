package ec.com.kruger.vaccination.api;

import ec.com.kruger.vaccination.api.dto.DetailByDateRq;
import ec.com.kruger.vaccination.api.dto.UpdateEmployeeRq;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.exception.UpdateException;
import ec.com.kruger.vaccination.model.DetailVaccination;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.service.DetailVaccinationService;
import ec.com.kruger.vaccination.service.EmployeeService;
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
public class DetailVaccinationControllerUnitTest {

    @Mock
    private DetailVaccinationService service;

    @InjectMocks
    private DetailByDateRq detailByDateRq;

    @InjectMocks
    DetailVaccination detailVaccination;

    @BeforeEach
    public void setUp() {
        detailVaccination = new DetailVaccination();
        detailByDateRq = new DetailByDateRq();
    }

    @Test
    public void givenDetailVaccinationReturnOk() {
        String identification = "testIdentification";
        DetailVaccinationController controller = new DetailVaccinationController(service);
        ResponseEntity response = ResponseEntity.ok().build();
        Assertions.assertEquals(response, controller.create(detailVaccination, identification));
    }

    @Test
    public void givenDetailVaccinationReturnBadRequest() {
        String identification = "testIdentification";
        DetailVaccinationController controller = new DetailVaccinationController(service);
        try {
            Mockito.doThrow(InsertException.class)
                    .when(service)
                    .createDetail(detailVaccination, identification);
        } catch (InsertException ex) {
            Logger.getLogger(DetailVaccinationControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.badRequest().build();
        Assertions.assertEquals(response, controller.create(detailVaccination, identification)  );
    }

    @Test
    public void givenTypeVaccineReturnDetails() {
        String typeVaccine = "typeVaccine";
        DetailVaccinationController controller = new DetailVaccinationController(service);
        List<DetailVaccination> details = new ArrayList<>();
        try {
            lenient().when(service.findDetailByTypeVaccine(typeVaccine))
                    .thenReturn(details);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(DetailVaccinationControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.ok(details);
        Assertions.assertEquals(response, controller.findDetailByTypeVaccine(typeVaccine));
    }

    @Test
    public void givenTypeVaccineReturnNotFound() {
        String typeVaccine = "typeVaccine";
        DetailVaccinationController controller = new DetailVaccinationController(service);
        try {
            Mockito.doThrow(DocumentNotFoundException.class)
                    .when(service)
                    .findDetailByTypeVaccine(typeVaccine);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(DetailVaccinationControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity responseNotFound = ResponseEntity.notFound().build();
        Assertions.assertEquals(responseNotFound, controller.findDetailByTypeVaccine(typeVaccine));
    }

    @Test
    public void givenDatesReturnDetails() {
        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = LocalDate.now();
        DetailByDateRq detailByDateRq = new DetailByDateRq();
        DetailVaccinationController controller = new DetailVaccinationController(service);
        List<DetailVaccination> details = new ArrayList<>();
        try {
            lenient().when(service.findDetailByDates(initialDate, finalDate))
                    .thenReturn(details);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(DetailVaccinationControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.ok(details);
        Assertions.assertEquals(response, controller.findDetailByDates(detailByDateRq));
    }

    @Test
    public void givenDatesReturnNotFound() {
        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = LocalDate.now();
        detailByDateRq = new DetailByDateRq();
        DetailVaccinationController controller = new DetailVaccinationController(service);
        try {
            Mockito.doThrow(DocumentNotFoundException.class)
                    .when(service)
                    .findDetailByDates(initialDate, finalDate);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(DetailVaccinationControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity responseNotFound = ResponseEntity.notFound().build();
        Assertions.assertEquals(responseNotFound, controller.findDetailByDates(detailByDateRq));
    }
}

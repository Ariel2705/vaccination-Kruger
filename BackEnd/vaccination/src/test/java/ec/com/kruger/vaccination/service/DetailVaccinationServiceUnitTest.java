package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.model.DetailVaccination;
import ec.com.kruger.vaccination.repository.DetailVaccinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DetailVaccinationServiceUnitTest {

    @Mock
    private DetailVaccinationService service;
    @Mock
    private DetailVaccinationRepository repository;

    @InjectMocks
    DetailVaccination detailVaccination;

    @BeforeEach
    public void setUp() {
        detailVaccination = new DetailVaccination();
    }

    @Test
    public void givenDetailAndIdentificationCreateDetail() {
        detailVaccination = new DetailVaccination();
        String identification = "testIdentification";

        try {
            service.createDetail(detailVaccination, identification);
            verify(repository, times(1)).save(detailVaccination);
        } catch (InsertException ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeServiceUnitTest.class.getSimpleName()).log(Level.SEVERE, null, ex);
        }
    }
}

package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.model.Parish;
import ec.com.kruger.vaccination.repository.ParishRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class ParishServiceUnitTest {

    @Mock
    private ParishRepository repository;

    @InjectMocks
    private ParishService service;
    private Parish parish;

    @BeforeEach
    public void setUp() {
        parish = new Parish();
    }

    @Test
    public void givenCantonReturnListOfParishes() {
        String canton = "132456789";
        List<Parish> parishes
                = repository.findByNameCanton(canton);
        try {
            Assertions.assertEquals(parishes, service.listParishesByNameCanton(canton));
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(ParishServiceUnitTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenBadCantonThrowDocumentNotFoundException() {
        String canton = "testCanton";
        Assertions.assertThrows(DocumentNotFoundException.class, () -> service.listParishesByNameCanton(canton));
    }

}

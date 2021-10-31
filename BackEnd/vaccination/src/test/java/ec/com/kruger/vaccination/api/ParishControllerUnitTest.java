package ec.com.kruger.vaccination.api;

import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.model.Parish;
import ec.com.kruger.vaccination.service.ParishService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParishControllerUnitTest {

    @Mock
    private ParishService service;

    @InjectMocks
    Parish parish;

    @BeforeEach
    public void setUp() {
        parish = new Parish();
    }

    @Test
    public void givenCantonReturnListOfParishes() {
        String canton = "Quito";
        ParishController controller = new ParishController(service);
        List<Parish> result = new ArrayList<>();
        try{
            when(service.listParishesByNameCanton(canton))
                    .thenReturn(result);
            Mockito.doThrow(DocumentNotFoundException.class)
                    .when(service)
                    .listParishesByNameCanton(null);
        } catch (DocumentNotFoundException ex) {
            Logger.getLogger(ParishControllerUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResponseEntity response = ResponseEntity.ok(result);
        ResponseEntity responseNotFound = ResponseEntity.notFound().build();
        Assertions.assertEquals(response, controller.findParishByCanton(canton));
        Assertions.assertEquals(responseNotFound, controller.findParishByCanton(null));
    }
}

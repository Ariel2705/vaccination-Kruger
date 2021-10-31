package ec.com.kruger.vaccination.api;

import ec.com.kruger.vaccination.model.Parish;
import ec.com.kruger.vaccination.service.ParishService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/kruger/parish")
public class ParishController {

    private final ParishService service;

    public ParishController(ParishService service){
        this.service = service;
    }

    @PreAuthorize("hasRole('ADM') OR hasRole('EMP')")
    @GetMapping("/findParishByCanton/{canton}")
    @ApiOperation(value = "Search for parishes by canton.",
            notes = "Returns all parishes by canton.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parishes found."),
            @ApiResponse(code = 404, message = "There are no parishes for this canton.")
    })
    public ResponseEntity<List<Parish>> findParishByCanton(@PathVariable String canton) {
        try{
            return ResponseEntity.ok(this.service.listParishesByNameCanton(canton));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package ec.com.kruger.vaccination.api;

import ec.com.kruger.vaccination.api.dto.DetailByDateRq;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.model.DetailVaccination;
import ec.com.kruger.vaccination.service.DetailVaccinationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/kruger/detailVaccination")
public class DetailVaccinationController {

    private final DetailVaccinationService service;

    public DetailVaccinationController(DetailVaccinationService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADM') OR hasRole('EMP')")
    @PostMapping("/createDetailVaccination/{identification}")
    @ApiOperation(value = "Creation of vaccination details.",
            notes = "Each vaccination detail has an assigned employee.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vaccination detail created."),
            @ApiResponse(code = 400, message = "Error creating an vaccination detail.")
    })
    public ResponseEntity<DetailVaccination> create(@RequestBody DetailVaccination detailVaccination
            , @PathVariable String identification) {
        try {
            this.service.createDetail(detailVaccination, identification);
            return ResponseEntity.ok().build();
        } catch (InsertException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ADM') OR hasRole('EMP')")
    @GetMapping("/findDetailByCodEmployee/{code}")
    @ApiOperation(value = "Search for vaccination details by employee code.",
            notes = "Search for vaccination details by employee code.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vaccination detail found."),
            @ApiResponse(code = 404, message = "There is no detail for this code.")
    })
    public ResponseEntity<List<DetailVaccination>> findDetailByCodEmployee(@PathVariable Integer code){
        try{
            return ResponseEntity.ok(this.service.findDetailByCodEmployee(code));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADM')")
    @GetMapping("/findDetailByTypeVaccine/{vaccine}")
    @ApiOperation(value = "Search for vaccination details by vaccine type.",
            notes = "Search for vaccination details by any of the 4 possible vaccine types.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vaccination detail found."),
            @ApiResponse(code = 404, message = "There is no detail for this vaccine.")
    })
    public ResponseEntity<List<DetailVaccination>> findDetailByTypeVaccine(@PathVariable String vaccine){
        try{
            return ResponseEntity.ok(this.service.findDetailByTypeVaccine(vaccine));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADM')")
    @PostMapping("/findDetailByDates")
    @ApiOperation(value = "Search for vaccination details by dates.",
            notes = "Search for vaccination details between two dates.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vaccination detail found."),
            @ApiResponse(code = 404, message = "There is no detail for this time.")
    })
    public ResponseEntity<List<DetailVaccination>> findDetailByDates(@RequestBody DetailByDateRq detailByDateRq) {
        try {
            return ResponseEntity.ok(this.service.findDetailByDates(
                    detailByDateRq.getInitialDate(),
                    detailByDateRq.getFinalDate()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

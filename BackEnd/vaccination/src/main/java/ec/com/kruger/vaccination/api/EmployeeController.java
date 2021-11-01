package ec.com.kruger.vaccination.api;

import ec.com.kruger.vaccination.api.dto.DetailByDateRq;
import ec.com.kruger.vaccination.api.dto.UpdateEmployeeRq;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.exception.UpdateException;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/kruger/employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADM')")
    @PostMapping("/createEmployee")
    @ApiOperation(value = "Employee creation",
            notes = "An employee is created in the employee table.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee created."),
            @ApiResponse(code = 400, message = "Error creating an employee.")
    })
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        try {
            this.service.createEmployee(employee);
            return ResponseEntity.ok().build();
        } catch (InsertException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ADM') OR hasRole('EMP')")
    @PutMapping("/completeDataEmployee")
    @ApiOperation(value = "Complete employee data.",
            notes = "The missing data of a created employee is completed.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete data by employee."),
            @ApiResponse(code = 400, message = "Error when completing employee data.")
    })
    public ResponseEntity<UpdateEmployeeRq> completeData(
            @RequestBody UpdateEmployeeRq updateEmployeeRq) {
        try {
            this.service.completeDataEmployee(updateEmployeeRq);
            return ResponseEntity.ok().build();
        } catch (UpdateException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ADM')")
    @PutMapping("/changeStateEmployee/{identification}")
    @ApiOperation(value = "Change of employee status.",
            notes = "Change to inactive employee status.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee in inactive status."),
            @ApiResponse(code = 400, message = "Error when changing employee status.")
    })
    public ResponseEntity<UpdateEmployeeRq> changeStateEmployee(
            @PathVariable String identification) {
        try {
            this.service.changeStateEmployee(identification);
            return ResponseEntity.ok().build();
        } catch (UpdateException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ADM') OR hasRole('EMP')")
    @GetMapping("/findEmployeeById/{identification}")
    @ApiOperation(value = "Search for an employee by their DNI.",
            notes = "There are no two or more employees registered in the database with the same DNI.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee found."),
            @ApiResponse(code = 404, message = "There is no employee with this DNI.")
    })
    public ResponseEntity<Employee> findEmployeeById(@PathVariable String identification) {
        try {
            return ResponseEntity.ok(this.service.findEmployeeByIdentification(identification));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADM')")
    @GetMapping("/findEmployeeByState/{stateVaccination}")
    @ApiOperation(value = "Search for employees by status",
            notes = "Employees are not deleted, their status is changed to inactive.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees with active status found."),
            @ApiResponse(code = 404, message = "No active employees.")
    })
    public ResponseEntity<List<Employee>> findEmployeeByState(
            @PathVariable String stateVaccination) {
        try {
            return ResponseEntity.ok(this.service.findEmployeeByStateVaccination(stateVaccination));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADM')")
    @GetMapping("/findEmployeeByTypeVaccine/{vaccine}")
    @ApiOperation(value = "Search for employees by type of vaccine applied.",
            notes = "Search for employees who have been vaccinated with any of the 4 possible vaccines.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees found by type of vaccine."),
            @ApiResponse(code = 404, message = "There are no employees vaccinated with this type of vaccine.")
    })
    public ResponseEntity<List<Employee>> findEmployeeByTypeVaccine(
            @PathVariable String vaccine) {
        try {
            return ResponseEntity.ok(this.service.findEmployeeByTypeVaccine(vaccine));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADM')")
    @PostMapping("/findEmployeeByDates")
    @ApiOperation(value = "Search for vaccinated employees in a range of dates.",
            notes = "Search for employees who were vaccinated between two dates.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees found by date."),
            @ApiResponse(code = 404, message = "There are no employees vaccinated at this time.")
    })
    public ResponseEntity<List<Employee>> findEmployeeByDates(@RequestBody DetailByDateRq detailByDateRq) {
        try {
            return ResponseEntity.ok(this.service.findEmployeeByDates(
                    detailByDateRq.getInitialDate(),
                    detailByDateRq.getFinalDate()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

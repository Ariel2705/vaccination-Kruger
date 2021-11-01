package ec.com.kruger.vaccination.api;

import ec.com.kruger.vaccination.api.dto.LoginRq;
import ec.com.kruger.vaccination.api.dto.UserRq;
import ec.com.kruger.vaccination.exception.CredentialInvalidException;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.service.EmployeeUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/kruger/security")
public class EmployeeUserController {

    private final EmployeeUserService service;

    public EmployeeUserController(EmployeeUserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    @ApiOperation(value = "User authentication.",
            notes = "Access to the system depending on the user role, "
                    + "returns a token for use in header (Authorization).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authorized access."),
            @ApiResponse(code = 400, message = "Incorrect credentials."),
            @ApiResponse(code = 404, message = "Incorrect user or password.")})
    public ResponseEntity<UserRq> login(@RequestBody LoginRq login) {
        try {
            return ResponseEntity
                    .ok(service.login(login.getUsername(), login.getPwd()));
        } catch (DocumentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CredentialInvalidException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ApiOperation(value = "Autenticación de ususarios",
            notes = "Se accede al sistema dependiendo del ROL del usuario, "
                    + "devuelve un token para utilizarlo en header (Authorization)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acceso autorizado"),
            @ApiResponse(code = 400, message = "Credenciales incorrectas"),
            @ApiResponse(code = 404, message = "Usuario incorrecto")})
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

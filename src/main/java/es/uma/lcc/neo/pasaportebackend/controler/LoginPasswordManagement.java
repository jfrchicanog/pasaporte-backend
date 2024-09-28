package es.uma.lcc.neo.pasaportebackend.controler;

import es.uma.lcc.neo.pasaportebackend.dto.ForgottenPasswordDTO;
import es.uma.lcc.neo.pasaportebackend.dto.JwtDTO;
import es.uma.lcc.neo.pasaportebackend.dto.LoginDTO;
import es.uma.lcc.neo.pasaportebackend.dto.PasswordResetDTO;
import es.uma.lcc.neo.pasaportebackend.exception.CredencialesIncorrectas;
import es.uma.lcc.neo.pasaportebackend.exception.TokenNoValidoException;
import es.uma.lcc.neo.pasaportebackend.exception.UsuarioInexistente;
import es.uma.lcc.neo.pasaportebackend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
@CrossOrigin()
@Tag(name="Gestión de usuarios", description="Operaciones para la gestión de usuarios")
public class LoginPasswordManagement {

    private UsuarioService usuarioService;

    public LoginPasswordManagement(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    @Operation(description = "Comprueba las credenciales del usuario y devuelve un JWT si todo fue correcto.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Devuelve un JWT para el usuario si las credenciales son correctas."),
                    @ApiResponse(responseCode = "401",
                            description = "Creedenciales no correctas.",
                            content = {@Content(schema = @Schema(implementation = Void.class))}),
            },
            security = {@SecurityRequirement(name = "public")})
    public ResponseEntity<JwtDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            String jwt = usuarioService.compruebaLogin(loginDTO.getEmail(), loginDTO.getPassword());
            return ResponseEntity.ok(JwtDTO.builder().jwt(jwt).build());
        } catch (UsuarioInexistente | CredencialesIncorrectas e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/passwordreset")
    @Operation(description = "Resetea la contraseña de un usuario al valor que se indica en el cuerpo"
            + " si el token de petición es correcto. En caso contrario devuelve un error.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Si el token de reseteo es correcto cambia la contraseña."),
                    @ApiResponse(responseCode = "400",
                            description = "Si el token de reseteo no es correcto.")
            },
            security = {@SecurityRequirement(name = "public")})
    public ResponseEntity passwordReset(@RequestBody PasswordResetDTO passwordReset) {
        usuarioService.passwordReset(passwordReset.getToken(), passwordReset.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgottenpassword")
    @Operation(description = "Pide al servicio que cree un enlace para resetear la contraseña"
            + " de un usuario. El servicio debe enviar un e-mail "
            + "con el enlace para resetear la contraseña."
            + " Si el usuario no existe, la respuesta debe ser la misma que "
            + "cuando existe, para no dar información acerca de la existencia del usuario "
            + "en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Si el usuario existe crear un enlace para resetear contraseña"
                                    + " y envía notificación. En caso contrario no hace nada.")
            },
            security = {@SecurityRequirement(name = "public")})
    public ResponseEntity forgottenPassword(@RequestBody ForgottenPasswordDTO forgottenPasswordDTO) {
        usuarioService.forgottenPassword(forgottenPasswordDTO.getEmail());
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(TokenNoValidoException.class)
    public ResponseEntity tokenNoValido(TokenNoValidoException e) {
        return ResponseEntity.badRequest().build();
    }

}

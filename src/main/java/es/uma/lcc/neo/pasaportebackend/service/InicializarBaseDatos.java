package es.uma.lcc.neo.pasaportebackend.service;

import es.uma.lcc.neo.pasaportebackend.entity.Rol;
import es.uma.lcc.neo.pasaportebackend.entity.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.logging.Logger;

@Component
public class InicializarBaseDatos {

    private final Logger log = Logger.getLogger(InicializarBaseDatos.class.getName());
    private final UsuarioService service;
    public InicializarBaseDatos(UsuarioService service) {
        this.service = service;
    }

    @PostConstruct
    public void inicializarSiNecesario() {
        if (service.findAll().isEmpty()) {
            log.info("No hay usuarios en la base de datos. Creando usuario admin");
            var usuario = Usuario.builder()
                    .nombre("Admin")
                    .apellido1("Admin")
                    .apellido2("Admin")
                    .email("admin@uma.es")
                    .hashContrasenia("admin")
                    .roles(Set.of(Rol.ADMINISTRADOR))
                    .build();
            service.save(usuario);
            usuario = Usuario.builder()
                    .nombre("Antonio")
                    .apellido1("García")
                    .apellido2("Ramos")
                    .email("antonio@uma.es")
                    .hashContrasenia("5678")
                    .roles(Set.of(Rol.EDITOR))
                    .build();
            service.save(usuario);
        }
    }

}

/*
 * Copyright "2025" Francisco Chicano, Javier Ferrer, Marina Calleja
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

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

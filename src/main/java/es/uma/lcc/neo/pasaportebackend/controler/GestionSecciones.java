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

package es.uma.lcc.neo.pasaportebackend.controler;

import es.uma.lcc.neo.pasaportebackend.dto.SeccionDTO;
import es.uma.lcc.neo.pasaportebackend.service.PasaporteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seccion")
@CrossOrigin()
@Tag(name="Gestión de las secciones de un pasaporte", description="Operaciones para la gestión de secciones de un pasaporte")
public class GestionSecciones {
    private PasaporteService pasaporteService;
    private Mapper mapper;

    public GestionSecciones(PasaporteService pasaporteService, Mapper mapper) {
        this.pasaporteService = pasaporteService;
        this.mapper = mapper;
    }

    //- POST /seccion?pasaporte={idPasaporte} (solo para editor)
    @PostMapping()
    public ResponseEntity<SeccionDTO> guardarSeccion(@RequestParam(required = true, name = "pasaporte") Long idPasaporte,
                                                     @RequestBody SeccionDTO seccion) {

        return ResponseEntity.ok(
            mapper.seccion(
                pasaporteService.aniadirSeccionAPasaporte(
                    idPasaporte,
                    mapper.seccion(seccion)
                )
            )
        );
    }

    //- PUT /seccion/{idSeccion} (solo editor)
    //- DELETE /seccion/{idSeccion} (solo editor)
}

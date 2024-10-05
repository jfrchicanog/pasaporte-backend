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

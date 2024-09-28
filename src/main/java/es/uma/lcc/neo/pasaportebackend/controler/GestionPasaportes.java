package es.uma.lcc.neo.pasaportebackend.controler;

import es.uma.lcc.neo.pasaportebackend.dto.PasaporteDTO;
import es.uma.lcc.neo.pasaportebackend.exception.PasaporteInexistente;
import es.uma.lcc.neo.pasaportebackend.exception.SinPermiso;
import es.uma.lcc.neo.pasaportebackend.service.PasaporteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pasaporte")
@CrossOrigin()
@Tag(name="Gestión de pasaportes", description="Operaciones para la gestión de pasaportes")
public class GestionPasaportes {

    private PasaporteService pasaporteService;
    private Mapper mapper;

    public GestionPasaportes(PasaporteService pasaporteService, Mapper mapper) {
        this.pasaporteService = pasaporteService;
        this.mapper = mapper;
    }

    //- GET /pasaporte/{id}
    @GetMapping("/{idPasaporte}")
    public ResponseEntity<PasaporteDTO> obtenerPasaporte(@PathVariable Long idPasaporte) {
        return ResponseEntity.of(
            pasaporteService.obtenerPasaporte(idPasaporte)
                .map(mapper::pasaporte)
            );
    }

    //- GET /pasaporte (solo para administrador y editor)
    @GetMapping()
    public ResponseEntity<List<PasaporteDTO>> obtenerPasaportes()  {
        return ResponseEntity.ok(
            pasaporteService.obtenerPasaportes()
                .stream()
                .map(mapper::pasaporte)
                .collect(Collectors.toList())
        );
    }

    //- POST /pasaporte (solo para editor)
    @PostMapping
    public ResponseEntity<PasaporteDTO> crearPasaporte(@RequestBody PasaporteDTO pasaporte) {

        return ResponseEntity.ok(
            mapper.pasaporte(
                pasaporteService.crearPasaporte(
                    mapper.pasaporte(pasaporte)
                )
            )
        );
    }

    //- PUT /pasaporte/{id} (solo para editor)
    //- DELETE /pasaporte/{id} (solo para editor y administrador)

    @ExceptionHandler(PasaporteInexistente.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void pasaporteInexistente() {
    }

    @ExceptionHandler(SinPermiso.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void sinPermiso() {
    }


}

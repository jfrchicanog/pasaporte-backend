package es.uma.lcc.neo.pasaportebackend.controler;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grupo")
@CrossOrigin()
@Tag(name="Gestión de grupos", description="Operaciones para la gestión de grupos")
public class GestionGrupos {
    //- GET /grupo (administrador y editor)
    //- POST /grupo (Administrador)
    //- GET /grupo/{idGrupo) (Admins)
    //- PUT /grupo/{idGrupo) (admin)
    //- DELETE /grupo/{idGrupo} (admin)
    //- PUT /grupo/{idGrupo}/asigna?usuario={idUsuarios} (admin)
    //- PUT /grupo/{idGrupo}/deasigna?usuario={idUsuarios} (admin)
}

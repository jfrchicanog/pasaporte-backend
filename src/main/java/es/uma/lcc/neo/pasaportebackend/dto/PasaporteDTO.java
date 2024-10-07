package es.uma.lcc.neo.pasaportebackend.dto;

import es.uma.lcc.neo.pasaportebackend.entity.Seccion;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class PasaporteDTO {
    private Long id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String foto;
    // TODO: mostramos solo la edad?
    private Date fechaNacimiento;
    private List<SeccionDTO> secciones;
}

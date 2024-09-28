package es.uma.lcc.neo.pasaportebackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeccionDTO {
    private Long id;
    private String nombre;
    private String contenido;
}

package es.uma.lcc.neo.pasaportebackend.controler;

import es.uma.lcc.neo.pasaportebackend.dto.PasaporteDTO;
import es.uma.lcc.neo.pasaportebackend.dto.SeccionDTO;
import es.uma.lcc.neo.pasaportebackend.entity.Pasaporte;
import es.uma.lcc.neo.pasaportebackend.entity.Seccion;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public PasaporteDTO pasaporte(Pasaporte pasaporte) {
        return PasaporteDTO.builder()
                .id(pasaporte.getId())
                .nombre(pasaporte.getNombre())
                .apellido1(pasaporte.getApellido1())
                .apellido2(pasaporte.getApellido2())
                .fechaNacimiento(pasaporte.getFechaNacimiento())
                .foto(new String(pasaporte.getFoto(), StandardCharsets.UTF_8))
                .secciones(
                    Optional.ofNullable(pasaporte.getSecciones())
                        .map(secciones -> secciones.stream()
                            .map(this::seccion)
                            .collect(Collectors.toList()))
                        .orElse(Collections.EMPTY_LIST)
                    )
                .build();
    }

    public SeccionDTO seccion(Seccion seccion) {
        return SeccionDTO.builder()
                .id(seccion.getId())
                .nombre(seccion.getNombre())
                .contenido(seccion.getContenido())
                .build();
    }

    public Pasaporte pasaporte(PasaporteDTO pasaporte) {
        return Pasaporte.builder()
                .id(pasaporte.getId())
                .nombre(pasaporte.getNombre())
                .apellido1(pasaporte.getApellido1())
                .apellido2(pasaporte.getApellido2())
                .fechaNacimiento(pasaporte.getFechaNacimiento())
                .foto(pasaporte.getFoto().getBytes(StandardCharsets.UTF_8))
                .secciones(
                    Optional.ofNullable(pasaporte.getSecciones())
                        .map(secciones -> secciones.stream()
                            .map(this::seccion)
                            .collect(Collectors.toList()))
                        .orElse(Collections.EMPTY_LIST)
                    )
                .build();
    }

    public Seccion seccion (SeccionDTO seccion) {
        return Seccion.builder()
                .id(seccion.getId())
                .nombre(seccion.getNombre())
                .contenido(seccion.getContenido())
                .build();
    }
}

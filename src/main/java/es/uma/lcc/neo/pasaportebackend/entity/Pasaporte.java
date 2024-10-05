package es.uma.lcc.neo.pasaportebackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pasaporte {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    private List<Seccion> secciones;
    private String nombre;
    private String apellido1;
    private String apellido2;
    @Lob
    private byte [] foto;
    private Date fechaNacimiento;

}

package es.uma.lcc.neo.pasaportebackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seccion {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    public Set<Visor> visores;
    private String nombre;
    private String contenido;
}

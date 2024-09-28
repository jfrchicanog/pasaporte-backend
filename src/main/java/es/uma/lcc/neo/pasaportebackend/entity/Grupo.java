package es.uma.lcc.neo.pasaportebackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Grupo extends Visor {
    public Grupo(Long id) {
        super(id);
    }
    private String nombre;
    @ManyToMany
    private Set<Usuario> usuarios;
}

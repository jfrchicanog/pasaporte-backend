package es.uma.lcc.neo.pasaportebackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Usuario extends Visor {
    @ManyToMany(mappedBy = "usuarios")
    private Set<Grupo> grupos;

    private String nombre;
    private String apellido1;
    private String apellido2;
    @Column(unique = true, nullable = false)
    private String email;
    @ElementCollection
    private Set<Rol> roles;
    private String hashContrasenia;

    @Builder
    public Usuario(Long id, Set<Grupo> grupos, String nombre, String apellido1, String apellido2,
                   String email, Set<Rol> roles, String hashContrasenia) {
        super(id);
        this.grupos = grupos;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.roles = roles;
        this.hashContrasenia = hashContrasenia;
    }
}

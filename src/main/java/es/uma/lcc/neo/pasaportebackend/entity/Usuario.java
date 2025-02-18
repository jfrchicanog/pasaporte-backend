/*
 * Copyright "2025" Francisco Chicano, Javier Ferrer, Marina Calleja
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

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

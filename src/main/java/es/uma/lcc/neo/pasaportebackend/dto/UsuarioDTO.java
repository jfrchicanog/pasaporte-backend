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

package es.uma.lcc.neo.pasaportebackend.dto;

import es.uma.lcc.neo.pasaportebackend.entity.Rol;
import es.uma.lcc.neo.pasaportebackend.entity.Usuario;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO extends UsuarioNuevoDTO {
	private Long id;

	@Builder
	public UsuarioDTO(Long id, String nombre, String apellido1, String apellido2,
					  String email, String password, Set<Rol> roles) {
		super(nombre, apellido1, apellido2, email, password, roles);
		this.id = id;
	}

	public static UsuarioDTO fromEntity(Usuario usuario) {
		return UsuarioDTO.builder()
				.id(usuario.getId())
				.nombre(usuario.getNombre())
				.apellido1(usuario.getApellido1())
				.apellido2(usuario.getApellido2())
				.email(usuario.getEmail())
				.roles(usuario.getRoles())
				.build();
	}

	public Usuario toEntity() {
		return Usuario.builder()
				.id(getId())
				.nombre(getNombre())
				.apellido1(getApellido1())
				.apellido2(getApellido2())
				.email(getEmail())
				.hashContrasenia(getPassword())
				.roles(getRoles())
				.build();
	}
}

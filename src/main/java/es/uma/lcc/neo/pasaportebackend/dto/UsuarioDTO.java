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

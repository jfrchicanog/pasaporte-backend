package es.uma.lcc.neo.pasaportebackend.dto;

import es.uma.lcc.neo.pasaportebackend.entity.Usuario;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO extends UsuarioNuevoDTO {
	private Long id;

	@Builder
	public UsuarioDTO(Long id, String nombre, String apellido1, String apellido2, String email, String password, Boolean administrador) {
		super(nombre, apellido1, apellido2, email, password, administrador);
		this.id = id;
	}

	public static UsuarioDTO fromEntity(Usuario usuario) {
		return UsuarioDTO.builder()
				.id(usuario.getId())
				.nombre(usuario.getNombre())
				.apellido1(usuario.getApellido1())
				.apellido2(usuario.getApellido2())
				.email(usuario.getEmail())
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

				.build();
	}
}

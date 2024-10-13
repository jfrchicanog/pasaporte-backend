package es.uma.lcc.neo.pasaportebackend.dto;

import es.uma.lcc.neo.pasaportebackend.entity.Rol;
import es.uma.lcc.neo.pasaportebackend.entity.Usuario;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "builderNuevo")
public class UsuarioNuevoDTO {
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private String password;
	private Set<Rol> roles;

	public Usuario toEntity() {
		return Usuario.builder()
				.nombre(this.nombre)
				.apellido1(this.apellido1)
				.apellido2(this.apellido2)
				.email(this.email)
				.roles(this.roles)
				.hashContrasenia(this.password)
				.build();
	}
}

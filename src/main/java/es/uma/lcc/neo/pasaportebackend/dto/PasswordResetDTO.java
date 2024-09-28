package es.uma.lcc.neo.pasaportebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDTO {
	private String token;
	private String password;
}

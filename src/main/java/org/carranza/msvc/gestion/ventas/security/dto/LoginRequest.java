package org.carranza.msvc.gestion.ventas.security.dto;

import lombok.Data;

@Data
public class LoginRequest {

	private String usuario;
	private String clave;

}

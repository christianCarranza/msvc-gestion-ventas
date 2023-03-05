package org.carranza.msvc.gestion.ventas.security.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenResponse {

	@Default
	private String type = "Bearer";
	private String token;
	private String refreshToken;
	private String user;
	private List<String> authorities;

}

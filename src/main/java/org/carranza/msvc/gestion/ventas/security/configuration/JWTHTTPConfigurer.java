
package org.carranza.msvc.gestion.ventas.security.configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import lombok.RequiredArgsConstructor;
import org.carranza.msvc.gestion.ventas.security.filter.JWTAuthenticationFilter;
import org.carranza.msvc.gestion.ventas.security.utils.JWTUtils;

@RequiredArgsConstructor
public class JWTHTTPConfigurer extends AbstractHttpConfigurer<JWTHTTPConfigurer, HttpSecurity> {

	private final JWTUtils jwtUtils;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		final AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		http.addFilter(new JWTAuthenticationFilter(authenticationManager, jwtUtils));
	}

}


package org.carranza.msvc.gestion.ventas.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.security.utils.Constants;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;
import org.carranza.msvc.gestion.ventas.security.utils.JWTUtils;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private JWTUtils jwtUtils;

	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
		this.authenticationManager = authenticationManager;
		this.jwtUtils=jwtUtils;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UsuarioEntity usuario = new ObjectMapper().readValue(request.getInputStream(), UsuarioEntity.class);

			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(usuario.getUsuario(),
					usuario.getClave(), new ArrayList<>());

			Authentication aut = authenticationManager.authenticate(upat);

			return aut;

		} catch (IOException e) {
			log.info("attemptAuthentication " + e.getMessage());
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		 if (request.getHeader(Constants.HEADER_REFRESH_TOKEN_KEY) == null) {
			 String refresh_token = jwtUtils.generateJwtToken(auth,true);
			 response.addHeader("Access-Control-Expose-Headers", Constants.HEADER_REFRESH_TOKEN_KEY);
             response.setHeader(Constants.HEADER_REFRESH_TOKEN_KEY, refresh_token);
         }
		
		String token = jwtUtils.generateJwtToken(auth, false);
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);

	}
}

package org.carranza.msvc.gestion.ventas.security.utils;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTUtils {
	
	@Autowired
	private UserDetailsService userDetailsService;

	public String generateJwtToken(String usuario, Boolean swRefreshToken) {

		UserDetails userDetails = userDetailsService.loadUserByUsername(usuario);

		Long time= (!swRefreshToken)?Constants.TOKEN_EXPIRATION_TIME_TOKEN:Constants.TOKEN_EXPIRATION_TIME_REFRESH_TOKEN;

		return Jwts
				.builder()
				.setIssuedAt(new Date()).setIssuer(Constants.ISSUER_INFO)
				.setSubject(usuario)
				.setExpiration(new Date(System.currentTimeMillis() + time))
				.claim(Constants.AUTHORITIES, userDetails.getAuthorities())
				.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY)
				.compact();
	}
	
	public String generateJwtFromTokenRefresh(String refreshToken) {
		String username = this.getUserNameFromJwtToken(refreshToken);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		return Jwts
						.builder()
						.setIssuedAt(new Date()).setIssuer(Constants.ISSUER_INFO)
						.setSubject(userDetails.getUsername())
						.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME_REFRESH_TOKEN))
						.claim(Constants.AUTHORITIES, userDetails.getAuthorities())
						.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY)
						.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(Constants.SUPER_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) throws Exception  {
		try {
			Jwts.parser().setSigningKey(Constants.SUPER_SECRET_KEY).parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		}

		return false;
	}
	
	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

}
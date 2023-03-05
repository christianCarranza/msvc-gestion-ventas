package org.carranza.msvc.gestion.ventas.security.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.carranza.msvc.gestion.ventas.security.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.security.dto.CodeResponse;
import org.carranza.msvc.gestion.ventas.security.dto.LoginRequest;
import org.carranza.msvc.gestion.ventas.security.dto.RefreshTokenRequest;
import org.carranza.msvc.gestion.ventas.security.dto.TokenResponse;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;
import org.carranza.msvc.gestion.ventas.security.exceptions.TokenException;
import org.carranza.msvc.gestion.ventas.security.exceptions.TokenRefreshException;
import org.carranza.msvc.gestion.ventas.security.service.UsuarioService;
import org.carranza.msvc.gestion.ventas.security.utils.JWTUtils;
import org.carranza.msvc.gestion.ventas.security.utils.TOTPUtil;

@Slf4j
@RestController
@RequestMapping("/auth")
public class ApiSecurity {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private TOTPUtil tOTPUtil;

	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws TokenException {
		try {

			log.info("loginRequest {}", loginRequest);

			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsuario());

			log.info("userDetails {}", userDetails);

			Boolean sw = bCryptPasswordEncoder.matches(loginRequest.getClave(), userDetails.getPassword());

			log.info("sw {}", sw);

			if (userDetails != null) {
				if (bCryptPasswordEncoder.matches(loginRequest.getClave(), userDetails.getPassword())) {

					String code = tOTPUtil.generateCode();
					// Save code
					usuarioService.updateCodigo2F(code, loginRequest.getUsuario());

					return ResponseEntity.ok().body(CodeResponse.builder().code(code).build());
				}
			}

			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

		} catch (Exception e) {
			System.out.println("attemptAuthentication " + e.getMessage());
			throw new SecurityException(e);
		}
	}

	@PostMapping("/verify-code/{code}")
	public ResponseEntity<?> verifyCode(@PathVariable String code) throws TokenException {
		try {

			log.info("code {}", code);

			if (tOTPUtil.verifyCode(code)) {

				UsuarioEntity usuarioEntity=usuarioService.findByUsuario(code);

				log.info("usuarioEntity {}", usuarioEntity);

				UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
						usuarioEntity.getUsuario(), usuarioEntity.getClave());

				Authentication authentication = authenticationManagerBuilder.getObject().authenticate(upat);

				SecurityContextHolder.getContext().setAuthentication(authentication);

				Authentication aut = authenticationManagerBuilder.getObject().authenticate(upat);

				UserDetails userDetails = (UserDetails) authentication.getPrincipal();

				String token = jwtUtils.generateJwtToken(aut, false);
				String refeshToken = jwtUtils.generateJwtToken(aut, true);
				List<String> authorities = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());
				return ResponseEntity.ok().body(TokenResponse.builder().user(userDetails.getUsername()).token(token)
						.refreshToken(refeshToken).authorities(authorities).build());
			}{
				log.info("Code is invalid");
			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("attemptAuthentication " + e.getMessage());
			throw new TokenException(e);
		}
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception {
		try {
			String tokenRequest = refreshTokenRequest.getToken();
			log.info("tokenRequest {}", tokenRequest);
			if (jwtUtils.validateJwtToken(tokenRequest)) {

				String token = jwtUtils.generateJwtFromTokenRefresh(tokenRequest);
				log.info("token with refresh {}", token);
				String user = jwtUtils.getUserNameFromJwtToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(user);
				List<String> authorities = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());
				return ResponseEntity.ok().body(TokenResponse.builder().user(user).token(token)
						.refreshToken(tokenRequest).authorities(authorities).build());
			}
			throw new TokenRefreshException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TokenRefreshException();

		}
	}
}

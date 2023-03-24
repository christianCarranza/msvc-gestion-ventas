package org.carranza.msvc.gestion.ventas.security.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.carranza.msvc.gestion.ventas.api.utils.CodeEnum;
import org.carranza.msvc.gestion.ventas.api.utils.CustomResponse;
import org.carranza.msvc.gestion.ventas.security.dto.*;
import org.carranza.msvc.gestion.ventas.security.entity.RolEntity;
import org.carranza.msvc.gestion.ventas.security.event.SecurityMailService;
import org.carranza.msvc.gestion.ventas.security.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;
import org.carranza.msvc.gestion.ventas.security.exceptions.TokenException;
import org.carranza.msvc.gestion.ventas.security.exceptions.TokenRefreshException;
import org.carranza.msvc.gestion.ventas.security.service.UsuarioService;
import org.carranza.msvc.gestion.ventas.security.utils.JWTUtils;
import org.carranza.msvc.gestion.ventas.security.utils.TOTPUtil;

import static org.carranza.msvc.gestion.ventas.security.utils.Constants.LOGIN_URL;
import static org.carranza.msvc.gestion.ventas.security.utils.Constants.REFRESH_TOKEN_URL;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/auth")
public class ApiSecurity {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final  UserDetailsService userDetailsService;

	private final JWTUtils jwtUtils;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final SecurityMailService securityMailService;

	private final UsuarioService usuarioService;

	private final TOTPUtil tOTPUtil;

	@Value("${mail.message}")
	private String mailMessage;

	@PostMapping("/login")
	public ResponseEntity<?> login(HttpServletRequest request, @RequestBody LoginRequest loginRequest) throws TokenException {
		try {

			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsuario());

			if (userDetails != null) {
				if (bCryptPasswordEncoder.matches(loginRequest.getClave(), userDetails.getPassword())) {

					MailResponse mailResponse= MailResponse.builder().usuario(loginRequest.getUsuario()).build();
					securityMailService.createCode(mailResponse);

					UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
							loginRequest.getUsuario(), loginRequest.getClave(), new ArrayList<>());

					Authentication authentication = authenticationManagerBuilder.getObject().authenticate(upat);

					upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);

					return ResponseEntity.ok().body(CodeResponse.builder().message(mailMessage).build());
				}
			}

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		} catch (Exception e) {
			throw new SecurityException(e);
		}

	}

	@PostMapping("/verify-code/{code}")
	public ResponseEntity<?> verifyCode(@PathVariable String code) throws TokenException {

		HttpHeaders headers = new HttpHeaders();
			if (tOTPUtil.verifyCode(code)) {

				UsuarioEntity usuarioEntity = usuarioService.findByCodigo(code);

				String usuario = usuarioEntity.getUsuario();

				log.info("usuarioEntity {}", usuarioEntity);

				String token = jwtUtils.generateJwtToken(usuario, false);
				String refeshToken = jwtUtils.generateJwtToken(usuario, true);

				headers.add("Access-Control-Expose-Headers", Constants.HEADER_REFRESH_TOKEN_KEY);
				headers.add(Constants.HEADER_REFRESH_TOKEN_KEY, refeshToken);

				headers.add("Access-Control-Expose-Headers", "Authorization");
				headers.add(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);

				CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), null, "Se genero el token");

				return new ResponseEntity<>(headers, HttpStatus.OK);

			}else {
				CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.ERROR), null, "Codigo invalido");
				return new ResponseEntity<>(rpta, HttpStatus.FORBIDDEN);
			}
	}

	@PostMapping(REFRESH_TOKEN_URL)
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

package org.carranza.msvc.gestion.ventas.security.exceptions;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalSecurityExceptions extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<Object> handleTokenException(TokenException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("message", "Token Exception");

		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}
	@ExceptionHandler(TokenRefreshException.class)
	public ResponseEntity<Object> handleTokenException(TokenRefreshException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("message", "Token Refresh Exception");

		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}

}

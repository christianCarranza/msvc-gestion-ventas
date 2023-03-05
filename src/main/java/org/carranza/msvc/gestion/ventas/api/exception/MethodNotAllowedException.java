package org.carranza.msvc.gestion.ventas.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends RuntimeException {
  private static final long serialVersionUID = 7054406864602863734L;

  public MethodNotAllowedException(String mensaje) {
    super(mensaje);
  }

}

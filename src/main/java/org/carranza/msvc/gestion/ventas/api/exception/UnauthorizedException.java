package org.carranza.msvc.gestion.ventas.api.exception;

public class UnauthorizedException extends RuntimeException {
  private static final long serialVersionUID = 673291884773230683L;
  private static final String DESCRIPTION = "Unauthorized Exception (401)";

  public UnauthorizedException(String mensaje) {
    super(DESCRIPTION + ". " + mensaje);
  }
}



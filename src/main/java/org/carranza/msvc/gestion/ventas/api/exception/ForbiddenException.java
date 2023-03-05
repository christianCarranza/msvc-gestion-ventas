package org.carranza.msvc.gestion.ventas.api.exception;

public class ForbiddenException extends RuntimeException {
  private static final long serialVersionUID = -8036250919018921540L;
  private static final String DESCRIPTION = "Forbidden Exception (403)";

  public ForbiddenException(String mensaje) {
    super(DESCRIPTION + ". " + mensaje);
  }
}

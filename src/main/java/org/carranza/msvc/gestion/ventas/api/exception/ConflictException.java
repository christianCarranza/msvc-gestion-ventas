package org.carranza.msvc.gestion.ventas.api.exception;

public class ConflictException extends RuntimeException {
  private static final long serialVersionUID = -1760339279781140377L;
  private static final String DESCRIPTION = "Conflict Exception (409)";

  public ConflictException(String mensaje) {
    super(DESCRIPTION + ". " + mensaje);
  }
}

package org.carranza.msvc.gestion.ventas.api.exception;

public class BadRequestException extends RuntimeException {
  private static final long serialVersionUID = -367545608214467341L;
  private static final String DESCRIPTION = "Bad Request Exception (400)";

  public BadRequestException(String mensaje) {
    super(DESCRIPTION + ". " + mensaje);
  }

  public BadRequestException(String DESCRIPTION, String mensaje){ super (DESCRIPTION + ". " + mensaje); }
}

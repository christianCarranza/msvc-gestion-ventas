package org.carranza.msvc.gestion.ventas.api.exception;

public class NotImplementedException extends RuntimeException {
  private static final long serialVersionUID = -6362773132614685055L;
  private static final String DESCRIPTION = "Not Implemented Exception (501)";

  public NotImplementedException(String mensaje) {
    super(DESCRIPTION + ". " + mensaje);
  }

  public NotImplementedException(String DESCRIPTION, String mensaje){ super(DESCRIPTION + ". " + mensaje); }
}

package org.carranza.msvc.gestion.ventas.api.exception;

public class MalformedHeaderException extends BadRequestException {
  private static final long serialVersionUID = -5468379005406300806L;
  private static final String DESCRIPTION = "Token with wrong format";

  public MalformedHeaderException(String mensaje) {
    super(DESCRIPTION + ". " + mensaje);
  }
}

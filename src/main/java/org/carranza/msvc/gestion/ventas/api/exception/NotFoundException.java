package org.carranza.msvc.gestion.ventas.api.exception;

public class NotFoundException extends RuntimeException {
  private static final long serialVersionUID = 4014940355533273918L;
  private static final String DESCRIPTION = "NotFound Exception (404)";

  public NotFoundException(String mensaje) {
    super(DESCRIPTION + ". " + mensaje);
  }

  public NotFoundException(String mensaje, String codigo){
    super(mensaje);
  }
}
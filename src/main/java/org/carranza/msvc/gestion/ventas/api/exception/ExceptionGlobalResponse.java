package org.carranza.msvc.gestion.ventas.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.api.utils.CustomResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class ExceptionGlobalResponse extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<CustomResponse> notFoundException(NotFoundException ex) {
    CustomResponse response = new CustomResponse("error", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<CustomResponse> badRequestException(BadRequestException ex) {
    CustomResponse response = new CustomResponse("error", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodNotAllowedException.class)
  public final ResponseEntity<CustomResponse> methodNotAllowedException(MethodNotAllowedException ex) {
    CustomResponse response = new CustomResponse("error", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(GestionVentasException.class)
  public final ResponseEntity<CustomResponse> vuceZeeExceptionPersonalizado(GestionVentasException ex) {
    log.error("Exception vuceZeeExceptionPersonalizado global");
    CustomResponse response = new CustomResponse(ex.getTipo(), ex.getMensaje());
    return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public final ResponseEntity<CustomResponse> accesoDenegadoException(AccessDeniedException ex) {
    log.error("Exception accesoDenegadoException global");
    CustomResponse response = new CustomResponse("error", "No estas autorizado para acceder a este recurso");
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(DataAccessException.class)
  public final ResponseEntity<CustomResponse> DataAccessException(DataAccessException ex) {
    log.error("Exception DataAccessException global inicio" + ex.getMessage());
    CustomResponse response = new CustomResponse("error", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<CustomResponse> manejarTodasExcepciones(Exception ex) {
    log.error("Exception accesoDenegadoException global Inicio");
    String message = ex.getCause() != null ? (ex.getCause().getCause() != null ? ex.getCause().getCause().getMessage() : ex.toString()) :
            ex.toString();
    CustomResponse response = new CustomResponse("error", message);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

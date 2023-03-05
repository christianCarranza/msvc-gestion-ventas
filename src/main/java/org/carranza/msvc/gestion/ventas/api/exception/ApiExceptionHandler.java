package org.carranza.msvc.gestion.ventas.api.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ApiExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({NotFoundException.class})
  @ResponseBody
  public ErrorMessage notFoundRequest(HttpServletRequest request, Exception exception) {
    return new ErrorMessage(exception, HttpStatus.NOT_FOUND.value(), request.getRequestURI());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
          BadRequestException.class,
          HttpRequestMethodNotSupportedException.class,
          MethodArgumentNotValidException.class,
          MissingRequestHeaderException.class,
          MissingServletRequestParameterException.class,
          MethodArgumentTypeMismatchException.class,
          HttpMessageNotReadableException.class
  })
  @ResponseBody
  public ErrorMessage badRequest(HttpServletRequest request, Exception exception) {
    return new ErrorMessage(exception, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler({
          ForbiddenException.class
  })
  @ResponseBody
  public ErrorMessage forbiddenRequest(HttpServletRequest request, Exception exception) {
    return new ErrorMessage(exception, HttpStatus.FORBIDDEN.value(), request.getRequestURI());
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler({
          ConflictException.class,
          DataIntegrityViolationException.class
  })
  @ResponseBody
  public ErrorMessage conflict(HttpServletRequest request, Exception exception) {
    return new ErrorMessage(exception, HttpStatus.CONFLICT.value(), request.getRequestURI());
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler({
          UnauthorizedException.class,
          AccessDeniedException.class
  })
  public void unauthorized() {
    //
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({
          Exception.class
  })
  @ResponseBody
  public ErrorMessage fatalErrorUnexpectedException(HttpServletRequest request, Exception exception) {
    return new ErrorMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
  }

  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @ExceptionHandler({
          NotImplementedException.class
  })
  @ResponseBody
  public ErrorMessage notImplementedException(HttpServletRequest request, Exception exception) {
    return new ErrorMessage(exception, HttpStatus.NOT_IMPLEMENTED.value(), request.getRequestURI());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({UnknownException.class})
  @ResponseBody
  public ErrorMessage unknownException(HttpServletRequest request, Exception exception) {
    return new ErrorMessage(exception, HttpStatus.FORBIDDEN.value(), request.getRequestURI());
  }
}

package ru.ifmo.volunteer.handler;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ifmo.volunteer.exception.AlreadyExistsException;
import ru.ifmo.volunteer.exception.AuthorizationException;
import ru.ifmo.volunteer.exception.ResourceNotFoundException;

@RestControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler(value = {ResourceNotFoundException.class, EmptyResultDataAccessException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<String> handle(final RuntimeException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = AlreadyExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handle(final AlreadyExistsException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = AuthorizationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<String> handle(final AuthorizationException e) {
    return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.FORBIDDEN);
  }
}

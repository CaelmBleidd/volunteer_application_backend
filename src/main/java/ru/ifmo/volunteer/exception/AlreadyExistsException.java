package ru.ifmo.volunteer.exception;

public class AlreadyExistsException extends RuntimeException {
  public AlreadyExistsException(final String message) {
    super(message);
  }
}

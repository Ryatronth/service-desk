package ru.ryatronth.sd.error.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends SdException {
  public ConflictException(String message) {
    super(HttpStatus.CONFLICT, message);
  }
}

package ru.ryatronth.sd.error.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends SdException {
  public UnauthorizedException(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }
}

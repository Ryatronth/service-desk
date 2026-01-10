package ru.ryatronth.sd.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class SdException extends RuntimeException {

  private final HttpStatus status;

  protected SdException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }

}

package ru.ryatronth.sd.file.exception;

import org.springframework.http.HttpStatus;
import ru.ryatronth.sd.error.exception.SdException;

public class FileIsNotReadyException extends SdException {

  public FileIsNotReadyException(String message) {
    super(HttpStatus.CONFLICT, message);
  }


}

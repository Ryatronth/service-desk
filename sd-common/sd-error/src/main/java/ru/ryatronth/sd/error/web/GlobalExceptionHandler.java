package ru.ryatronth.sd.error.web;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ryatronth.sd.error.exception.SdException;
import ru.ryatronth.sd.error.web.response.ErrorFieldViolation;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  private final ErrorResponseFactory factory;

  public GlobalExceptionHandler(ErrorResponseFactory factory) {
    this.factory = factory;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex,
                                            HttpServletRequest request) {
    List<ErrorFieldViolation> violations =
        ex.getBindingResult().getFieldErrors().stream().map(this::toViolation)
            .collect(Collectors.toList());

    var body = factory.build(HttpStatus.BAD_REQUEST, "Validation failed", request, violations);

    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(SdException.class)
  public ResponseEntity<?> handleSdException(SdException ex, HttpServletRequest request) {
    var body = factory.build(ex.getStatus(), ex.getMessage(), request, List.of());

    log.warn("Handle SdException", ex);

    return ResponseEntity.status(ex.getStatus()).body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleUnknown(Exception ex, HttpServletRequest request) {
    var body = factory.build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request, List.of());

    log.error("Handle Exception", ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }

  private ErrorFieldViolation toViolation(FieldError fe) {
    return new ErrorFieldViolation(fe.getField(),
        fe.getDefaultMessage() == null ? "Invalid value" : fe.getDefaultMessage());
  }

}

package ru.ryatronth.sd.error.web;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import ru.ryatronth.sd.error.config.properties.SdErrorProperties;
import ru.ryatronth.sd.error.web.response.ErrorFieldViolation;
import ru.ryatronth.sd.error.web.response.ErrorResponse;

@RequiredArgsConstructor
public class ErrorResponseFactory {

  private final SdErrorProperties properties;

  public ErrorResponse build(HttpStatus status,
                             String message,
                             HttpServletRequest request,
                             List<ErrorFieldViolation> violations) {
    String traceId = properties.isIncludeTraceId() ? MDC.get(properties.getTraceIdMdcKey()) : null;

    return new ErrorResponse(Instant.now(),
        status.value(),
        status.getReasonPhrase(),
        message,
        request.getRequestURI(),
        traceId,
        violations == null ? List.of() : List.copyOf(violations));
  }

}

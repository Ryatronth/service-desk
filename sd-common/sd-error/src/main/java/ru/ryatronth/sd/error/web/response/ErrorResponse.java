package ru.ryatronth.sd.error.web.response;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String message,
    String path,
    String traceId,
    List<ErrorFieldViolation> violations
) {
}

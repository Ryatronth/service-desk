package ru.ryatronth.sd.error.web.response;

public record ErrorFieldViolation(
    String field,
    String message
) {
}

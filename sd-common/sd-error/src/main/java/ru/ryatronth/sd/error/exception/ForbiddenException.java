package ru.ryatronth.sd.error.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends SdException {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

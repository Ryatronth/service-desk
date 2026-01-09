package ru.ryatronth.sd.error.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends SdException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

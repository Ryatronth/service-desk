package ru.ryatronth.sd.error.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends SdException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

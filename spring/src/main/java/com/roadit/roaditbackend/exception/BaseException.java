package com.roadit.roaditbackend.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    protected BaseException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}

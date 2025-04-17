package com.roadit.roaditbackend.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BaseException {
    public DuplicateEmailException() {
        super("DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
    }
}

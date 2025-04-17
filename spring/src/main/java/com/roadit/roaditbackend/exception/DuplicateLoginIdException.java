package com.roadit.roaditbackend.exception;

import org.springframework.http.HttpStatus;

public class DuplicateLoginIdException extends BaseException {
    public DuplicateLoginIdException() {
        super("DUPLICATE_ID", "이미 사용 중인 로그인 ID입니다.", HttpStatus.BAD_REQUEST);
    }
}

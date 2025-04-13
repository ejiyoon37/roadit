package com.roadit.roaditbackend.exception;

import org.springframework.http.HttpStatus;

public class DuplicateNicknameException extends BaseException {
    public DuplicateNicknameException() {
        super("DUPLICATE_NICKNAME", "이미 사용 중인 닉네임입니다.", HttpStatus.BAD_REQUEST);
    }
}

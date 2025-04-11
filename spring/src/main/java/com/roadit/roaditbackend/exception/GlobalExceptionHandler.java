package com.roadit.roaditbackend.exception;

import com.roadit.roaditbackend.dto.ApiResponse;
import com.roadit.roaditbackend.exception.DuplicateEmailException;
import com.roadit.roaditbackend.exception.DuplicateNicknameException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateEmail(DuplicateEmailException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateNickname(DuplicateNicknameException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(DuplicateLoginIdException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateLoginId(DuplicateLoginIdException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(e.getMessage(), e.getClass().getSimpleName()));
    }
}
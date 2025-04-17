package com.roadit.roaditbackend.exception;

import com.roadit.roaditbackend.dto.ApiResponse;
import com.roadit.roaditbackend.exception.DuplicateEmailException;
import com.roadit.roaditbackend.exception.DuplicateNicknameException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : "유효성 검사에 실패했습니다.";
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(message, "ValidationError"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("잘못된 요청 형식입니다.", "TypeMismatch"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAll(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("서버 오류가 발생했습니다.", "InternalServerError"));
    }

}
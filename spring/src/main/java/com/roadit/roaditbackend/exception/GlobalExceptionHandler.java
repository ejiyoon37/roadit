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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


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
        log.error("🚨 처리되지 않은 예외 발생", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("서버 오류가 발생했습니다.", "InternalServerError"));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleUnreadable(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("요청 본문이 잘못되었습니다.", "HttpMessageNotReadable"));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("필수 입력값이 누락되었거나 잘못된 형식입니다.", "DataIntegrityViolation"));
    }
}
package com.roadit.roaditbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> error(String message, String type) {
        return new ApiResponse<>(false, null, new ErrorResponse(message, type));
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private String message;
        private String type;
    }
}

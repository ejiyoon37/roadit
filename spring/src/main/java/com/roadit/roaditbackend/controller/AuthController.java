package com.roadit.roaditbackend.controller;

import com.roadit.roaditbackend.dto.ApiResponse;
import com.roadit.roaditbackend.dto.SignupRequest;
import com.roadit.roaditbackend.dto.SignupResponse;
import com.roadit.roaditbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody @Valid SignupRequest request) {
        SignupResponse response = authService.signup(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
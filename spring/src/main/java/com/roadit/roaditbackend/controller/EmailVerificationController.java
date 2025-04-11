package com.roadit.roaditbackend.controller;

import com.roadit.roaditbackend.dto.ApiResponse;
import com.roadit.roaditbackend.service.EmailService;
import com.roadit.roaditbackend.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService verificationService;
    private final EmailService emailService;

    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<Map<String, String>>> sendVerificationCode(@RequestParam String email) {
        try {
            String code = verificationService.createAndSendVerification(email);

            String subject = "roadit 이메일 인증번호";
            String body = """
                    <h2>인증번호입니다</h2>
                    <p>아래 코드를 입력해주세요.</p>
                    <h1>%s</h1>
                    """.formatted(code);

            emailService.sendVerificationEmail(email, subject, body);

            Map<String, String> result = new HashMap<>();
            result.put("message", "인증번호가 전송되었습니다.");
            return ResponseEntity.ok(ApiResponse.success(result));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("인증번호 전송에 실패했습니다.", e.getClass().getSimpleName()));
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Map<String, String>>> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean success = verificationService.verifyCode(email, code);

        if (success) {
            Map<String, String> result = new HashMap<>();
            result.put("message", "인증이 완료되었습니다.");
            return ResponseEntity.ok(ApiResponse.success(result));
        } else {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("인증번호가 일치하지 않습니다.", "InvalidVerificationCode"));
        }
    }
}

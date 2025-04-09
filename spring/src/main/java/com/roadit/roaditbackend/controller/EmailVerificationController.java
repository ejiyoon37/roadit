package com.roadit.roaditbackend.controller;

import com.roadit.roaditbackend.service.EmailService;
import com.roadit.roaditbackend.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService verificationService;
    private final EmailService emailService;

    @PostMapping("/send-code")
    public ResponseEntity<Map<String, Object>> sendVerificationCode(@RequestParam String email) {
        try {
            String code = verificationService.createAndSendVerification(email);

            String subject = "roadit 이메일 인증번호";
            String body = """
                <h2>인증번호</h2>
                <p>아래 숫자를 입력해주세요.</p>
                <h1>%s</h1>
                """.formatted(code);
            emailService.sendVerificationEmail(email, subject, body);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true); // 인증번호 전송 성공
            return ResponseEntity.ok(response); // 200 OK
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false); // 인증번호 전송 실패
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 Internal Server Error
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Map<String, Object>> verifyCode(@RequestParam String email, @RequestParam String code) {
        Map<String, Object> response = new HashMap<>();
        boolean success = verificationService.verifyCode(email, code);

        if (success) {
            response.put("success", true); // 인증 성공
            return ResponseEntity.ok(response); // 200 OK
        } else {
            response.put("success", false); // 인증 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
        }
    }
}

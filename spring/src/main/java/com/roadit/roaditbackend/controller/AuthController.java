package com.roadit.roaditbackend.controller;

import com.roadit.roaditbackend.dto.SignupRequest;
import com.roadit.roaditbackend.entity.EmailToken;
import com.roadit.roaditbackend.entity.User;
import com.roadit.roaditbackend.repository.EmailTokenRepository;
import com.roadit.roaditbackend.repository.UserRepository;
import com.roadit.roaditbackend.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final EmailTokenRepository emailTokenRepository;
    private final EmailService emailService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        User user = new User(request.getEmail(), request.getPassword(), request.getUsername());
        userRepository.save(user);

        EmailToken emailToken = EmailToken.create(request.getEmail());
        emailTokenRepository.save(emailToken);

        String link = "http://localhost:8080/api/auth/verify-email?token=" + emailToken.getId();
        String subject = "roadit 이메일 인증";
        String body = """
            <h2>이메일 인증</h2>
            <a href="%s">인증하기</a>
            """.formatted(link);

        emailService.sendVerificationEmail(request.getEmail(), subject, body);

        return "회원가입 성공! 이메일을 확인해주세요.";
    }

    @GetMapping("/verify-email")
    @Transactional
    public String verifyEmail(@RequestParam("token") String token) {
        EmailToken emailToken = emailTokenRepository.findById(token)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 토큰입니다."));

        if (emailToken.isExpired()) {
            return "토큰이 만료되었습니다.";
        }

        User user = userRepository.findByEmail(emailToken.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일 유저 없음"));

        user.verifyEmail();
        emailTokenRepository.delete(emailToken);

        return "이메일 인증 완료!";
    }
}

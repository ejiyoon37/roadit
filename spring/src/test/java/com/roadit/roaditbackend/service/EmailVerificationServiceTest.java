package com.roadit.roaditbackend.service;

import com.roadit.roaditbackend.entity.EmailVerification;
import com.roadit.roaditbackend.repository.EmailVerificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class EmailVerificationServiceTest {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Test
    void shouldSendVerificationCode() {
        String email = "testuser@example.com";

        String code = emailVerificationService.createAndSendVerification(email);

        Optional<EmailVerification> result = emailVerificationRepository.findByEmail(email);
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo(code);
        assertThat(result.get().isVerified()).isFalse();
    }

    @Test
    void shouldVerifyCodeSuccessfully() {
        String email = "verifiable@example.com";
        String code = "ABC123";
        EmailVerification verification = EmailVerification.builder()
                .email(email)
                .code(code)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();
        emailVerificationRepository.save(verification);

        boolean result = emailVerificationService.verifyCode(email, code);

        assertThat(result).isTrue();
        assertThat(emailVerificationRepository.findByEmail(email).get().isVerified()).isTrue();
    }

    @Test
    void shouldFailVerificationWithWrongCode() {
        String email = "wrongcode@example.com";
        String code = "RIGHT123";
        emailVerificationRepository.save(EmailVerification.builder()
                .email(email)
                .code(code)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build());

        boolean result = emailVerificationService.verifyCode(email, "WRONG456");

        assertThat(result).isFalse();
    }

    @Test
    void shouldFailVerificationWithExpiredCode() {
        String email = "expired@example.com";
        String code = "EXPIRED";
        emailVerificationRepository.save(EmailVerification.builder()
                .email(email)
                .code(code)
                .expiresAt(LocalDateTime.now().minusMinutes(1)) // expired
                .verified(false)
                .build());

        boolean result = emailVerificationService.verifyCode(email, code);

        assertThat(result).isFalse();
    }
}

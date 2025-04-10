package com.roadit.roaditbackend.service;

import com.roadit.roaditbackend.entity.EmailVerification;
import com.roadit.roaditbackend.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository repository;

    public String createAndSendVerification(String email) {
        String code = generateCode();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        saveOrUpdateVerificationCode(email, code, expiresAt);
        return code;
    }

    public boolean verifyCode(String email, String code) {
        Optional<EmailVerification> optional = repository.findByEmail(email);
        return optional.filter(ev -> ev.isValid(code)).map(ev -> {
            ev.setVerified(true);
            repository.save(ev);
            return true;
        }).orElse(false);
    }

    @Transactional
    public void saveOrUpdateVerificationCode(String email, String code, LocalDateTime expiresAt) {
        EmailVerification existing = repository.findByEmail(email).orElse(null);

        if (existing != null) {
            existing.setCode(code);
            existing.setExpiresAt(expiresAt);
            existing.setVerified(false);
            repository.save(existing); // update
        } else {
            EmailVerification newVerification = EmailVerification.builder()
                    .email(email)
                    .code(code)
                    .expiresAt(expiresAt)
                    .verified(false)
                    .build();
            repository.save(newVerification); // insert
        }
    }

    private String generateCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}

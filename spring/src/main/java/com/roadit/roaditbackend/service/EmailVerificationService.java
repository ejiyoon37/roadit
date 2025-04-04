package com.roadit.roaditbackend.service;

import com.roadit.roaditbackend.entity.EmailVerification;
import com.roadit.roaditbackend.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository repository;

    public String createAndSendVerification(String email) {
        String code = generateCode();
        EmailVerification ev = EmailVerification.create(email, code, 5);
        repository.save(ev);
        return code;
    }

    public boolean verifyCode(String email, String code) {
        return repository.findById(email)
                .filter(ev -> ev.isValid(code))
                .map(ev -> {
                    ev.setVerified(true);
                    repository.save(ev);
                    return true;
                }).orElse(false);
    }

    private String generateCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}

package com.roadit.roaditbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EmailVerification {

    @Id
    private String email;

    private String code;

    private boolean verified;

    private LocalDateTime expiresAt;

    public static EmailVerification create(String email, String code, int validMinutes) {
        EmailVerification ev = new EmailVerification();
        ev.setEmail(email);
        ev.setCode(code);
        ev.setVerified(false);
        ev.setExpiresAt(LocalDateTime.now().plusMinutes(validMinutes));
        return ev;
    }

    public boolean isValid(String inputCode) {
        return !verified
                && code.equals(inputCode)
                && expiresAt.isAfter(LocalDateTime.now());
    }
}

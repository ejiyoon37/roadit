package com.roadit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class EmailToken {

    @Id
    private String id;  // UUID 기반 토큰

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    public static EmailToken create(String userEmail) {
        EmailToken token = new EmailToken();
        token.id = UUID.randomUUID().toString();  // 랜덤 UUID
        token.userEmail = userEmail;
        token.expirationTime = LocalDateTime.now().plusMinutes(30);  // 30분 유효
        return token;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationTime);
    }
}
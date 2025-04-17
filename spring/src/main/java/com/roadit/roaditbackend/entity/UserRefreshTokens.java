package com.roadit.roaditbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_tokens")
public class UserRefreshTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String refreshToken;

    private String deviceInfo;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private boolean active;

    @PrePersist
    public void prePersist() {

        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusDays(7);
        this.active = true;
    }

}
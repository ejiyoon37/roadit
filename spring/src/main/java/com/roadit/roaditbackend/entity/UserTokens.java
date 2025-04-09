package com.roadit.roaditbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.roadit.roaditbackend.entity.Users;

@Entity
@Table(name = "user_tokens")
public class UserTokens {

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

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
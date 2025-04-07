package com.roadit.roaditbackend.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_login_providers")
@Getter
@NoArgsConstructor
public class UserLoginProvider {

    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // enum LoginType: GOOGLE, ROADIT 등
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginType provider;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(nullable = true)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public UserLoginProvider(String id, User user, LoginType provider, String loginId, String password) {
        this.id = id;
        this.user = user;
        this.provider = provider;
        this.loginId = loginId;
        this.password = password;
    }
}
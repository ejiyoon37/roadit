package com.roadit.roaditbackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.roadit.roaditbackend.enums.LoginType;
import com.roadit.roaditbackend.entity.Users;


@Entity
@Table(name = "user_login_providers")
@Getter
@NoArgsConstructor
public class UserLoginProviders {

    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

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

    public UserLoginProviders(String id, Users user, LoginType provider, String loginId, String password) {
        this.id = id;
        this.user = user;
        this.provider = provider;
        this.loginId = loginId;
        this.password = password;
    }
}


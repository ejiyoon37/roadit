package com.roadit.roaditbackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class Users {

    @Id //google sub
    @Column(length = 36)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    private String nickname;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "nation_id")
    private Integer nation;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "job_id")
    private Integer job;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "school_id")
    private Integer school;

    @Column(nullable = false)
    private String name;

    @Column(name = "residence_period")
    private String residencePeriod;

    @Column(name = "will_settle")
    private Boolean willSettle = false;

    private String role;

    private Integer level;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public Users(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
package com.roadit.roaditbackend.repository;

import com.roadit.roaditbackend.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, String> {
}

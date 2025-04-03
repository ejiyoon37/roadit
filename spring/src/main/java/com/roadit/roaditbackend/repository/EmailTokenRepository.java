package com.roadit.repository;

import com.roadit.entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {
}
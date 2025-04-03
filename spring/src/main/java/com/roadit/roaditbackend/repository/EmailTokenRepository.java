package com.roadit.roaditbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roadit.roaditbackend.entity.EmailToken;

public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {
}
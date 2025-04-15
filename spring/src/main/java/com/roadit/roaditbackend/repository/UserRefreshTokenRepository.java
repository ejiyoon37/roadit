package com.roadit.roaditbackend.repository;

import com.roadit.roaditbackend.entity.UserRefreshTokens;
import com.roadit.roaditbackend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshTokens, Long> {
    Optional<UserRefreshTokens> findByUser(Users user);
    void deleteByUser(Users user);
}
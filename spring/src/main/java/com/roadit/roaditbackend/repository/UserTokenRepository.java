package com.roadit.roaditbackend.repository;

import com.roadit.roaditbackend.entity.Users;
import com.roadit.roaditbackend.entity.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserTokens, Long> {
    Optional<UserTokens> findByRefreshToken(String refreshToken);
    List<UserTokens> findByUser(Users user);
    void deleteByRefreshToken(String refreshToken);
}
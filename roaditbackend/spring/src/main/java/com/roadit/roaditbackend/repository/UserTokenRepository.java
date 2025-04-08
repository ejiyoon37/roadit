package com.roadit.roaditbackend.domain.user.repository;

import com.roadit.roaditbackend.domain.user.entity.User;
import com.roadit.roaditbackend.domain.user.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByRefreshToken(String refreshToken);
    List<UserToken> findByUser(User user);
    void deleteByRefreshToken(String refreshToken);
}
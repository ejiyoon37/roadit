package com.roadit.roaditbackend.domain.user.repository;

import com.roadit.roaditbackend.domain.user.entity.UserLoginProvider;
import com.roadit.roaditbackend.domain.user.entity.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginProviderRepository extends JpaRepository<UserLoginProvider, String> {
    Optional<UserLoginProvider> findByLoginIdAndProvider(String loginId, LoginType provider);
}
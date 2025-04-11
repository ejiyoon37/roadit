package com.roadit.roaditbackend.repository;

import com.roadit.roaditbackend.entity.UserLoginProviders;
import com.roadit.roaditbackend.enums.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginProviderRepository extends JpaRepository<UserLoginProviders, String> {
    Optional<UserLoginProviders> findByLoginIdAndProvider(String loginId, LoginType provider);
    boolean existsByLoginId(String loginId);
}
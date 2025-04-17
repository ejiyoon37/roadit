package com.roadit.roaditbackend.service;

import com.roadit.roaditbackend.dto.*;
import com.roadit.roaditbackend.entity.UserLoginProviders;
import com.roadit.roaditbackend.enums.LoginType;
import com.roadit.roaditbackend.repository.UserLoginProviderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserLoginProviderRepository providerRepository;

    private String generateUnique(String base) {
        return base + "_" + System.currentTimeMillis();
    }

    @Test
    void signup_success() {
        SignupRequest request = new SignupRequest();
        request.setLoginId(generateUnique("testId"));
        request.setEmail(generateUnique("test") + "@example.com");
        request.setNickname(generateUnique("nickname"));
        request.setPassword("Password123!");
        request.setProvider(LoginType.ROADIT);
        request.setName("Test User");
        request.setNation("1");
        request.setJob("1");
        request.setSchool("1");
        request.setResidencePeriod(1);
        request.setWillSettle(true);

        SignupResponse response = authService.signup(request);
        assertThat(response.getUserId()).isNotNull();
    }

    @Test
    void reset_password_success() {
        String loginId = generateUnique("resetId");
        String email = generateUnique("reset") + "@example.com";

        // 먼저 회원가입
        SignupRequest request = new SignupRequest();
        request.setLoginId(loginId);
        request.setEmail(email);
        request.setNickname(generateUnique("resetNick"));
        request.setPassword("Password123!");
        request.setProvider(LoginType.ROADIT);
        request.setName("Reset User");
        request.setNation("1");
        request.setJob("1");
        request.setSchool("1");
        request.setResidencePeriod(1);
        request.setWillSettle(true);

        authService.signup(request);

        // 비밀번호 초기화
        PasswordResetRequest resetRequest = new PasswordResetRequest();
        resetRequest.setLoginId(loginId);
        resetRequest.setEmail(email);

        String result = authService.resetPassword(resetRequest);
        assertThat(result).contains("임시 비밀번호가 이메일로 전송");
    }

    @Test
    void change_password_success() {
        String loginId = generateUnique("changeId");
        String email = generateUnique("change") + "@example.com";
        String originalPassword = "Original123!";
        String newPassword = "NewPass456!";

        SignupRequest request = new SignupRequest();
        request.setLoginId(loginId);
        request.setEmail(email);
        request.setNickname(generateUnique("changeNick"));
        request.setPassword(originalPassword);
        request.setProvider(LoginType.ROADIT);
        request.setName("Change User");
        request.setNation("1");
        request.setJob("1");
        request.setSchool("1");
        request.setResidencePeriod(1);
        request.setWillSettle(true);

        authService.signup(request);

        PasswordChangeRequest changeRequest = new PasswordChangeRequest();
        changeRequest.setLoginId(loginId);
        changeRequest.setCurrentPassword(originalPassword);
        changeRequest.setNewPassword(newPassword);

        authService.changePassword(changeRequest);

        UserLoginProviders provider = providerRepository
                .findByLoginIdAndProvider(loginId, LoginType.ROADIT)
                .orElseThrow();
        assertThat(provider.getPassword()).isNotEqualTo(originalPassword);
    }

    @Test
    void change_password_fail_wrong_current_password() {
        String loginId = generateUnique("wrongCurrent");
        String email = generateUnique("wrong") + "@example.com";
        String originalPassword = "Correct123!";
        String wrongPassword = "Wrong123!";
        String newPassword = "NewOne456!";

        SignupRequest request = new SignupRequest();
        request.setLoginId(loginId);
        request.setEmail(email);
        request.setNickname(generateUnique("wrongNick"));
        request.setPassword(originalPassword);
        request.setProvider(LoginType.ROADIT);
        request.setName("Wrong Password User");
        request.setNation("1");
        request.setJob("1");
        request.setSchool("1");
        request.setResidencePeriod(1);
        request.setWillSettle(true);

        authService.signup(request);

        PasswordChangeRequest changeRequest = new PasswordChangeRequest();
        changeRequest.setLoginId(loginId);
        changeRequest.setCurrentPassword(wrongPassword);
        changeRequest.setNewPassword(newPassword);

        assertThrows(IllegalArgumentException.class, () -> {
            authService.changePassword(changeRequest);
        });
    }
}
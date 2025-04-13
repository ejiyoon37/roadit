package com.roadit.roaditbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadit.roaditbackend.dto.LoginRequest;
import com.roadit.roaditbackend.repository.UserLoginProviderRepository;
import com.roadit.roaditbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class AuthControllerLoginTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;
    @Autowired private UserLoginProviderRepository userLoginProviderRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        // 테스트용 유저 생성 (이미 있는 경우 생략 가능)
        // 테스트에 필요한 기본 유저를 DB에 넣고 싶다면 여기에 작성
    }

    @Test
    void roaditLoginSuccess() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setLoginId("testuser");
        request.setPassword("testpassword");

        mockMvc.perform(post("/api/login/roadit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }

    @Test
    void roaditLoginFail_WrongPassword() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setLoginId("testuser");
        request.setPassword("testpassword");

        mockMvc.perform(post("/api/login/roadit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.message").value("로그인 ID 또는 비밀번호가 잘못되었습니다."));
    }
}

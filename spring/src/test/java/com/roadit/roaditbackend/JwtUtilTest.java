package com.roadit.roaditbackend;

import com.roadit.roaditbackend.entity.Users;
import com.roadit.roaditbackend.security.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    private final Users testUser = new Users(1L, "test@example.com", "TestUser");

    @Test
    void testGenerateAndValidateAccessToken() {
        String token = jwtUtil.generateAccessToken(testUser);
        Assertions.assertNotNull(token);
        Assertions.assertTrue(jwtUtil.validateToken(token));
        Assertions.assertEquals("1", jwtUtil.getUserIdFromToken(token));
    }

    @Test
    void testGenerateAndValidateRefreshToken() {
        String refreshToken = jwtUtil.generateRefreshToken(testUser);
        Assertions.assertNotNull(refreshToken);
        Assertions.assertTrue(jwtUtil.validateToken(refreshToken));
        Assertions.assertEquals("1", jwtUtil.getUserIdFromToken(refreshToken));
    }
}
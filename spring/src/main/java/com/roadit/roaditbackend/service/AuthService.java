package com.roadit.roaditbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadit.roaditbackend.dto.*;
import com.roadit.roaditbackend.entity.UserRefreshTokens;
import com.roadit.roaditbackend.entity.Users;
import com.roadit.roaditbackend.entity.UserLoginProviders;
import com.roadit.roaditbackend.enums.UserStatus;
import com.roadit.roaditbackend.enums.LoginType;
import com.roadit.roaditbackend.exception.DuplicateEmailException;
import com.roadit.roaditbackend.exception.DuplicateNicknameException;
import com.roadit.roaditbackend.exception.DuplicateLoginIdException;
import com.roadit.roaditbackend.repository.*;
import com.roadit.roaditbackend.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;



@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserLoginProviderRepository userLoginProviderRepository;
    private final PasswordEncoder passwordEncoder;
    private final NationsRepository nationsRepository;
    private final JobsRepository jobsRepository;
    private final SchoolsRepository schoolsRepository;
    private final EmailService emailService;
    private final UserLoginProviderRepository providerRepository;
    private final UserRefreshTokenRepository refreshTokenRepository;


    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public SignupResponse signup(SignupRequest request) {
        if (request.getProvider() == LoginType.ROADIT &&
                userLoginProviderRepository.existsByLoginId(request.getLoginId())) {
            throw new DuplicateLoginIdException();
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new DuplicateNicknameException();
        }

        Users user = Users.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .name(request.getName())
                .nation(nationsRepository.findById(Long.parseLong(request.getNation()))
                        .orElseThrow(() -> new IllegalArgumentException("국가 정보가 유효하지 않습니다.")))
                .job(jobsRepository.findById(Long.parseLong(request.getJob()))
                        .orElseThrow(() -> new IllegalArgumentException("직업 정보가 유효하지 않습니다.")))
                .school(schoolsRepository.findById(Long.parseLong(request.getSchool()))
                        .orElseThrow(() -> new IllegalArgumentException("학교 정보가 유효하지 않습니다.")))
                .residencePeriod(request.getResidencePeriod())
                .willSettle(request.getWillSettle())
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(user);

        UserLoginProviders.UserLoginProvidersBuilder providerBuilder = UserLoginProviders.builder()
                .user(user)
                .provider(request.getProvider());

        if (request.getProvider() == LoginType.ROADIT) {
            providerBuilder
                    .loginId(request.getLoginId())
                    .password(passwordEncoder.encode(request.getPassword()));
        }

        userLoginProviderRepository.save(providerBuilder.build());

        return new SignupResponse("회원가입이 완료되었습니다.", user.getId());
    }

    @Transactional
    public String resetPassword(PasswordResetRequest request) {
        UserLoginProviders provider = userLoginProviderRepository
                .findByLoginIdAndProvider(request.getLoginId(), LoginType.ROADIT)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 로그인 ID가 없습니다."));

        if (!provider.getUser().getEmail().equals(request.getEmail())) {
            throw new IllegalArgumentException("입력하신 정보와 일치하는 계정을 찾을 수 없습니다.");
        }

        String newPassword = UUID.randomUUID().toString().substring(0, 10);
        String encoded = passwordEncoder.encode(newPassword);
        provider.setPassword(encoded);
        userLoginProviderRepository.save(provider);


        String subject = "roadit 비밀번호 초기화 안내";
        String body = String.format("""
        <h2>비밀번호 초기화 완료</h2>
        <p>새 비밀번호는 <strong>%s</strong> 입니다.</p>
        <p>로그인 후 꼭 비밀번호를 변경해 주세요.</p>
        """, newPassword);

        emailService.sendVerificationEmail(request.getEmail(), subject, body);

        return "임시 비밀번호가 이메일로 전송되었습니다. 이메일을 확인해 주세요.";
    }

    @Transactional
    public void changePassword(PasswordChangeRequest request) {
        UserLoginProviders provider = providerRepository.findByLoginIdAndProvider(request.getLoginId(), LoginType.ROADIT)
                .orElseThrow(() -> new IllegalArgumentException("해당 로그인 ID의 유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getCurrentPassword(), provider.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        provider.setPassword(passwordEncoder.encode(request.getNewPassword()));
        providerRepository.save(provider);
    }

    public void saveRefreshToken(Users user, String token, String deviceInfo) {
        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);

        UserRefreshTokens newToken = new UserRefreshTokens();
        newToken.setUser(user);
        newToken.setRefreshToken(token);
        newToken.setDeviceInfo(deviceInfo);
        refreshTokenRepository.save(newToken);
    }

    public LoginResponse login(@Valid LoginRequest request) {
        UserLoginProviders provider = userLoginProviderRepository
                .findByLoginIdAndProvider(request.getLoginId(), LoginType.ROADIT)
                .orElseThrow(() -> new IllegalArgumentException("로그인 ID 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), provider.getPassword())) {
            throw new IllegalArgumentException("로그인 ID 또는 비밀번호가 올바르지 않습니다.");
        }

        Users user = provider.getUser();

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        saveRefreshToken(user, refreshToken, "web");

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse googleLogin(GoogleLoginRequest request) {
        String userInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";
        String url = userInfoEndpoint + "?access_token=" + request.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        String response = responseEntity.getBody();

        JsonNode profile;
        try {
            profile = objectMapper.readTree(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Google response", e);
        }

        if (profile.has("error")) {
            String errorDescription = profile.path("error_description").asText("Invalid token");
            throw new IllegalArgumentException("Google token error: " + errorDescription);
        }

        String email = profile.get("email").asText();
        String name = profile.get("name").asText();

        Users user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    Users newUser = Users.builder()
                            .email(email)
                            .name(name)
                            .status(UserStatus.ACTIVE)
                            .build();
                    userRepository.save(newUser);

                    UserLoginProviders provider = UserLoginProviders.builder()
                            .user(newUser)
                            .provider(LoginType.GOOGLE)
                            .build();
                    userLoginProviderRepository.save(provider);

                    return newUser;
                });

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        saveRefreshToken(user, refreshToken, "web");

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String userId = jwtUtil.getUserIdFromToken(refreshToken);

        Users user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        saveRefreshToken(user, newRefreshToken, "web");

        return new LoginResponse(newAccessToken, newRefreshToken);
    }

   @Transactional
   public void logout(LogoutRequest request) {
       Users user = userRepository.findById(request.getUserId())
               .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

       refreshTokenRepository.findByUserAndDeviceInfo(user, request.getDeviceInfo())
               .ifPresent(refreshTokenRepository::delete);
   }
}

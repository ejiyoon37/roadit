package com.roadit.roaditbackend.service;

import com.roadit.roaditbackend.dto.SignupRequest;
import com.roadit.roaditbackend.dto.SignupResponse;
import com.roadit.roaditbackend.entity.Users;
import com.roadit.roaditbackend.entity.UserLoginProviders;
import com.roadit.roaditbackend.enums.UserStatus;
import com.roadit.roaditbackend.enums.LoginType;
import com.roadit.roaditbackend.exception.DuplicateEmailException;
import com.roadit.roaditbackend.exception.DuplicateNicknameException;
import com.roadit.roaditbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserLoginProviderRepository providerRepository;
    private final PasswordEncoder passwordEncoder;
    private final NationsRepository nationsRepository;
    private final JobsRepository jobsRepository;
    private final SchoolsRepository schoolsRepository;

    public SignupResponse signup(SignupRequest request) {
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

        providerRepository.save(providerBuilder.build());

        return new SignupResponse("회원가입이 완료되었습니다.", user.getId());
    }
}

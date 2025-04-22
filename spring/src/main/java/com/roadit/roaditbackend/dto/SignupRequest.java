package com.roadit.roaditbackend.dto;

import com.roadit.roaditbackend.enums.LanguageType;
import com.roadit.roaditbackend.enums.Nation;
import com.roadit.roaditbackend.enums.LoginType;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class SignupRequest {

    @Email
    @NotBlank
    private String email;

    private String loginId;

    private String password;

    @NotBlank
    private String nickname;

    private String name; // nullable 허용

    @NotNull
    private Nation nation;

    @NotNull
    private LanguageType language;

    @NotBlank
    private String residencePeriod;

    @NotNull
    private LoginType provider; // ROADIT or GOOGLE
}

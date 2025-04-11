package com.roadit.roaditbackend.dto;

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

    @NotBlank
    private String name;

    @NotBlank
    private String nation;

    @NotBlank
    private String job;

    @NotBlank
    private String school;

    @NotNull
    @Min(0)
    private Integer residencePeriod;

    @NotNull
    private Boolean willSettle;

    @NotNull
    private LoginType provider; // ROADIT or GOOGLE
}
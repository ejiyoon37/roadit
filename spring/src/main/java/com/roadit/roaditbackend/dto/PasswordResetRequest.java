package com.roadit.roaditbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String email;
}

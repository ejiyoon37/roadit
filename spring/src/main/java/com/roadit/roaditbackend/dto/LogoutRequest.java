package com.roadit.roaditbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {
    private Long userId;
    private String deviceInfo;
}
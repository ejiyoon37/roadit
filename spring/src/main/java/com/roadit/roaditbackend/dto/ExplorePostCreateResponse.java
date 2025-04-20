package com.roadit.roaditbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExplorePostCreateResponse {
    private String message;
    private Long postId;
}

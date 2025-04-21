package com.roadit.roaditbackend.dto;

import com.roadit.roaditbackend.enums.LanguageType;
import com.roadit.roaditbackend.enums.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExplorePostRequest {

    @NotNull
    private Long editorId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private LanguageType language;

    @NotBlank
    private String tag;

    @NotNull
    private PostStatus status;

    @NotNull
    private List<String> imageUrls;
}

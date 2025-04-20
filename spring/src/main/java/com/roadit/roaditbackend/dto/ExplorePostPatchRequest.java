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
@NoArgsConstructor
@AllArgsConstructor
public class ExplorePostPatchRequest {
    private String title;
    private String content;
    private LanguageType language;
    private String tag;
    private PostStatus status;
    private List<String> imageUrls;
}

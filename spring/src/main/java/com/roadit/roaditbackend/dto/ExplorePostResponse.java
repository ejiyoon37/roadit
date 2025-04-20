package com.roadit.roaditbackend.dto;

import com.roadit.roaditbackend.enums.LanguageType;
import com.roadit.roaditbackend.enums.PostStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExplorePostResponse {

    private Long id;
    private Long editorId;
    private String title;
    private String content;
    private LanguageType language;
    private String tag;
    private PostStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> imageUrls;
}

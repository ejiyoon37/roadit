package com.roadit.roaditbackend.controller;

import com.roadit.roaditbackend.dto.*;
import com.roadit.roaditbackend.service.ExplorePostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/explore")
@RequiredArgsConstructor
public class ExplorePostAdminController {

    private final ExplorePostService explorePostService;

    // 게시물 등록
    @PostMapping
    public ResponseEntity<ApiResponse<ExplorePostCreateResponse>> create(@RequestBody @Valid ExplorePostRequest request) {
        Long postId = explorePostService.createExplorePost(request);

        ExplorePostCreateResponse response = ExplorePostCreateResponse.builder()
                .message("Explore 게시물이 등록되었습니다.")
                .postId(postId)
                .build();

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 게시물 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CommonMessageResponse>> patch(
            @PathVariable Long id,
            @RequestBody ExplorePostPatchRequest request) {

        explorePostService.patchExplorePost(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(new CommonMessageResponse("Explore 게시물이 일부 수정되었습니다."))
        );
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CommonMessageResponse>> delete(@PathVariable Long id) {
        explorePostService.deleteExplorePost(id);

        return ResponseEntity.ok(
                ApiResponse.success(new CommonMessageResponse("Explore 게시물이 삭제되었습니다."))
        );
    }
}

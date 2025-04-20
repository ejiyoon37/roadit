package com.roadit.roaditbackend.controller;

import com.roadit.roaditbackend.dto.ApiResponse;
import com.roadit.roaditbackend.dto.ExplorePostResponse;
import com.roadit.roaditbackend.service.ExplorePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/explore")
@RequiredArgsConstructor
public class ExplorePostController {

    private final ExplorePostService explorePostService;

    // 전체 게시물 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExplorePostResponse>>> getAll() {
        List<ExplorePostResponse> posts = explorePostService.getAllExplorePosts();
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    // 게시물 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExplorePostResponse>> getById(@PathVariable Long id) {
        ExplorePostResponse post = explorePostService.getExplorePost(id);
        return ResponseEntity.ok(ApiResponse.success(post));
    }
}

package com.roadit.roaditbackend.controller;

import com.roadit.roaditbackend.dto.ApiResponse;
import com.roadit.roaditbackend.entity.ExplorePost;
import com.roadit.roaditbackend.security.JwtUtil;
import com.roadit.roaditbackend.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;


import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {


    private final WishService wishService;
    private final JwtUtil jwtUtil;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<String>> toggleWishlist(
            @PathVariable Long postId,
            HttpServletRequest request) {

        String token = extractToken(request);
        Long userId = Long.parseLong(jwtUtil.getUserIdFromToken(token));

        boolean added = wishService.toggleWish(userId, postId);
        String msg = added ? "가보고 싶어요 등록됨" : "가보고 싶어요 취소됨";

        return ResponseEntity.ok(ApiResponse.success(msg));
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Authorization header is missing or invalid");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ExplorePost>>> getWishedPosts(@PathVariable Long userId) {
        List<ExplorePost> wishedPosts = wishService.getWishedPostsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(wishedPosts));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<String>> deleteWishlist(
            @PathVariable Long postId,
            HttpServletRequest request) {

        String token = extractToken(request);
        Long userId = Long.parseLong(jwtUtil.getUserIdFromToken(token));

        wishService.removeWish(userId, postId);
        return ResponseEntity.ok(ApiResponse.success("가보고 싶어요 항목이 삭제되었습니다."));
    }

}
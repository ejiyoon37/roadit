package com.roadit.roaditbackend.controller;

import com.roadit.roaditbackend.dto.ApiResponse;
import com.roadit.roaditbackend.service.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageUploadController {

    private final S3UploaderService s3UploaderService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadImage(
            @RequestPart("file") MultipartFile file,
            @RequestParam(defaultValue = "images") String dir
    ) {
        try {
            String url = s3UploaderService.upload(file, dir);
            return ResponseEntity.ok(ApiResponse.success(url));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ApiResponse.error("파일 업로드 실패: " + e.getMessage(), "UploadError")
            );
        }
    }
}

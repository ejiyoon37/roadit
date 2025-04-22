package com.roadit.roaditbackend.service;

import com.roadit.roaditbackend.dto.ExplorePostRequest;
import com.roadit.roaditbackend.dto.ExplorePostResponse;
import com.roadit.roaditbackend.dto.ExplorePostPatchRequest;
import com.roadit.roaditbackend.entity.ExplorePost;
import com.roadit.roaditbackend.entity.Images;
import com.roadit.roaditbackend.entity.Users;
import com.roadit.roaditbackend.entity.Wishlist;
import com.roadit.roaditbackend.enums.TargetType;
import com.roadit.roaditbackend.repository.ExplorePostRepository;
import com.roadit.roaditbackend.repository.ImagesRepository;
import com.roadit.roaditbackend.repository.UserRepository;
import com.roadit.roaditbackend.repository.WishlistRepository;
import com.roadit.roaditbackend.enums.PostStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Collections;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExplorePostService {

    private final ExplorePostRepository explorePostRepository;
    private final ImagesRepository imagesRepository;
    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;

    @Transactional
    public Long createExplorePost(ExplorePostRequest request) {
        LocalDateTime now = LocalDateTime.now();

        ExplorePost post = new ExplorePost();
        post.setEditorId(request.getEditorId());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setLanguage(request.getLanguage());
        post.setTag(request.getTag());
        post.setStatus(request.getStatus());
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        ExplorePost saved = explorePostRepository.save(post);

        List<Images> images = request.getImageUrls().stream()
                .map(url -> {
                    Images image = new Images();
                    image.setTargetId(saved.getId());
                    image.setTargetType(TargetType.EXPLORE_POST);
                    image.setImageUrl(url);
                    image.setStatus(1); // 기본 1 = 활성
                    image.setCreatedAt(now);
                    image.setUpdatedAt(now);
                    return image;
                }).collect(Collectors.toList());

        imagesRepository.saveAll(images);

        return saved.getId();
    }

    @Transactional
    public ExplorePostResponse getExplorePost(Long id) {
        ExplorePost post = explorePostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        List<String> imageUrls = imagesRepository
                .findByTargetIdAndTargetType(id, TargetType.EXPLORE_POST)
                .stream()
                .map(Images::getImageUrl)
                .collect(Collectors.toList());

        return ExplorePostResponse.builder()
                .id(post.getId())
                .editorId(post.getEditorId())
                .title(post.getTitle())
                .content(post.getContent())
                .language(post.getLanguage())
                .tag(post.getTag())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .imageUrls(imageUrls)
                .build();
    }

    @Transactional
    public List<ExplorePostResponse> getAllExplorePosts() {
        List<ExplorePost> posts = explorePostRepository.findByStatus(PostStatus.ACTIVE);

        List<Long> postIds = posts.stream()
                .map(ExplorePost::getId)
                .collect(Collectors.toList());

        List<Images> allImages = imagesRepository.findByTargetIdInAndTargetType(postIds, TargetType.EXPLORE_POST);

        Map<Long, List<String>> imageMap = allImages.stream()
                .collect(Collectors.groupingBy(
                        Images::getTargetId,
                        Collectors.mapping(Images::getImageUrl, Collectors.toList())
                ));

        return posts.stream()
                .map(post -> ExplorePostResponse.builder()
                        .id(post.getId())
                        .editorId(post.getEditorId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .language(post.getLanguage())
                        .tag(post.getTag())
                        .status(post.getStatus())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .imageUrls(imageMap.getOrDefault(post.getId(), Collections.emptyList()))
                        .build()
                ).collect(Collectors.toList());
    }

    @Transactional
    public void patchExplorePost(Long id, ExplorePostPatchRequest request) {
        ExplorePost post = explorePostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getLanguage() != null) {
            post.setLanguage(request.getLanguage());
        }
        if (request.getTag() != null) {
            post.setTag(request.getTag());
        }
        if (request.getStatus() != null) {
            post.setStatus(request.getStatus());
        }
        post.setUpdatedAt(LocalDateTime.now());
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            imagesRepository.deleteAllByTargetIdAndTargetType(id, TargetType.EXPLORE_POST);

            List<Images> newImages = request.getImageUrls().stream()
                    .map(url -> {
                        Images img = new Images();
                        img.setTargetId(id);
                        img.setTargetType(TargetType.EXPLORE_POST);
                        img.setImageUrl(url);
                        img.setStatus(1);
                        img.setCreatedAt(LocalDateTime.now());
                        img.setUpdatedAt(LocalDateTime.now());
                        return img;
                    }).collect(Collectors.toList());

            imagesRepository.saveAll(newImages);
        }
    }




    @Transactional
    public void deleteExplorePost(Long id) {
        ExplorePost post = explorePostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        post.setStatus(com.roadit.roaditbackend.enums.PostStatus.DELETED);
        post.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public boolean toggleWish(Long userId, Long postId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
        ExplorePost post = explorePostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        Optional<Wishlist> wish = wishlistRepository.findByUserAndPost(user, post);

        if (wish.isPresent()) {
            wishlistRepository.delete(wish.get());
            return false;
        } else {
            Wishlist newWish = new Wishlist();
            newWish.setUser(user);
            newWish.setPost(post);
            wishlistRepository.save(newWish);
            return true;
        }
    }


}

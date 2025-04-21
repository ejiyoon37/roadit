package com.roadit.roaditbackend.repository;

import com.roadit.roaditbackend.entity.ExplorePost;
import com.roadit.roaditbackend.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExplorePostRepository extends JpaRepository<ExplorePost, Long> {
    List<ExplorePost> findByStatus(PostStatus status);
}

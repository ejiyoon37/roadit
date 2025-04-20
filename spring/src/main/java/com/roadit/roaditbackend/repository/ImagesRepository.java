package com.roadit.roaditbackend.repository;

import com.roadit.roaditbackend.entity.Images;
import com.roadit.roaditbackend.enums.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByTargetIdAndTargetType(Long targetId, TargetType targetType);
    List<Images> findByTargetIdInAndTargetType(List<Long> targetIds, TargetType targetType);
    void deleteAllByTargetIdAndTargetType(Long targetId, TargetType targetType);
}

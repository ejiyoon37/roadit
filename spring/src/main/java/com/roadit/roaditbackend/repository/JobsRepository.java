package com.roadit.roaditbackend.repository;

import com.roadit.roaditbackend.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<Jobs, Long> {
}

package com.roadit.roaditbackend.repository;

import com.roadit.roaditbackend.entity.Schools;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolsRepository extends JpaRepository<Schools, Long> {
}

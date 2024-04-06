package com.java.app.ws.Repository;

import com.java.app.ws.entity.ProjectEntity;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByProjectTitleContaining(String title);
    List<ProjectEntity> findByDateCreateBetween(LocalDate start, LocalDate end);
    List<ProjectEntity> findByCreateDateBetween(LocalDate startDate, LocalDate endDate);

    List<ProjectEntity> findByUserId(Long userId);

    Optional<ProjectEntity> findByIdAndUserId(Long projectId, Long userId);
    List<ProjectEntity> findByProjectTitleContainingOrDescriptionContaining(String title, String description);
    Optional<ProjectEntity> findById(Long id);

}
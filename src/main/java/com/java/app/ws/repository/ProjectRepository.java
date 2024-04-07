package com.java.app.ws.repository;

import com.java.app.ws.entity.ProjectEntity;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByTitleContaining(String project_title);
    List<ProjectEntity> findByCreateDateBetween(LocalDate start, LocalDate end);


    List<ProjectEntity> findByUserId(Long id);


    Optional<ProjectEntity> findByIdAndUserId(Long ip_p, Long id);
    List<ProjectEntity> findByTitleContainingOrDescriptionContaining(String title, String description);
    Optional<ProjectEntity> findById(Long id);

}
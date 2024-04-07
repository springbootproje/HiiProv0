package com.java.app.ws.Repository;

import com.java.app.ws.Entity.ProjectEntity;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByProject_titleContaining(String project_title);
    List<ProjectEntity> findByCreate_dateBetween(LocalDate start, LocalDate end);


    List<ProjectEntity> findByUserId(Long id);


    Optional<ProjectEntity> findByIdAndUserId(Long ip_p, Long id);
    List<ProjectEntity> findByProject_titleContainingOrDescriptionContaining(String title, String description);
    Optional<ProjectEntity> findById(Long id);

}
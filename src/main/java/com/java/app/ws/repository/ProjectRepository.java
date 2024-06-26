
package com.java.app.ws.repository;

import com.java.app.ws.dto.ProjectDto;
import com.java.app.ws.entity.ProjectEntity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByTitleContaining(String title);

    List<ProjectDto> findByCreateDateGreaterThanEqual(LocalDate startDate);

    @EntityGraph(attributePaths = {"members"})
    List<ProjectEntity> findAll();







    List<ProjectEntity> findByTitleContainingOrDescriptionContaining(String title, String description);
    Optional<ProjectEntity> findById(Long id);


    List<ProjectEntity> findByTitle(String title);


}

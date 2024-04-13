package com.java.app.ws.service;


import com.java.app.ws.dto.ProjectCreationDto;
import com.java.app.ws.dto.ProjectDetailsDto;
import com.java.app.ws.dto.ProjectDto;
import com.java.app.ws.dto.ProjectSummaryDto;
import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service

public interface ProjectService {
    //ProjectDto createProject(ProjectCreationDto projectCreationDto);
    ProjectEntity createProject(String title, String description, Long creatorUserId);
    List<ProjectSummaryDto> getAllProjectSummaries();

    List<ProjectSummaryDto> searchByTitle(String title);

    void deleteProject(Long id);

    List<ProjectDto> getProjectsFromStartDate(LocalDate date);













//hadou li lteht mazal mandirhoum

    //ProjectDto getProjectById(Long id);
   // List<ProjectEntity> getAllProjects();



   // List<ProjectEntity> searchProjects(String keyword);
   // List<ProjectEntity> findAllProjectsByUserId(Long userId);

   // void deleteUsersProject(Long projectId, Long userId);

    //ProjectEntity transferProjectToAnotherUser(Long projectId, Long newOwnerId);

}

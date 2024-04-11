package com.java.app.ws.service;

import com.java.app.ws.dto.*;
import com.java.app.ws.repository.ProjectRepository;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository =userRepository;
    }



    @Transactional
    @Override
    public ProjectDto createProject(ProjectCreationDto projectCreationDto) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setTitle(projectCreationDto.getTitle());
        projectEntity.setDescription(projectCreationDto.getDescription());
        projectEntity.setCreateDate(LocalDate.now()); // La date de création est la date actuelle

        UserEntity userEntity = userRepository.findById(projectCreationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + projectCreationDto.getUserId()));

        projectEntity.setUser(userEntity);

        projectEntity = projectRepository.save(projectEntity);

        ProjectDto projectDto = new ProjectDto();
        BeanUtils.copyProperties(projectEntity, projectDto);
        projectDto.setUserId(userEntity.getId());

        return projectDto;
    }


    @Override
    public List<ProjectSummaryDto> getAllProjectSummaries() {
        List<ProjectEntity> projectEntities = projectRepository.findAll();
        return projectEntities.stream().map(entity -> {
            ProjectSummaryDto dto = new ProjectSummaryDto();
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ProjectSummaryDto> searchByTitle(String title) {
        List<ProjectEntity> projectEntities = projectRepository.findByTitleContaining(title);
        return projectEntities.stream().map(entity -> {
            ProjectSummaryDto dto = new ProjectSummaryDto();
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Long id) {
        // Delete a projet par ID
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + id));
        projectRepository.delete(project);
    }


    @Override //hadi baqili fiha qlq modif
    public List<ProjectDto> getProjectsFromStartDate(LocalDate startDate) {
        return projectRepository.findByCreateDateGreaterThanEqual(startDate);
    }






    //hadou li lteht mazal mandirhoum


    @Override
    public List<ProjectEntity> findAllProjectsByUserId(Long userId) {
        return null;
    }

    public ProjectEntity updateProject(Long id, ProjectEntity projectDetails) {
        //  existing project and update its details
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + id));

        projectEntity.setTitle(projectDetails.getTitle());
        projectEntity.setDescription(projectDetails.getDescription());
        // Optionally allow updating the creation date or other fields
        // Do not update create date but you can add a field called update_date that you automatically update at this moment


        return projectRepository.save(projectEntity);
    }


    @Override
    public void deleteUsersProject(Long projectId, Long userId) {
        ProjectEntity project = projectRepository.findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new RuntimeException("Project not found or not owned by user"));
        projectRepository.delete(project);
    }

    public ProjectEntity updateProjectDetails(Long projectId, ProjectEntity projectDetails, Long userId) {
        ProjectEntity projectEntity = projectRepository.findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new RuntimeException("Project not found or not owned by user"));


        projectEntity.setTitle(projectDetails.getTitle());
        projectEntity.setDescription(projectDetails.getDescription());
        projectEntity.setCreateDate(projectDetails.getCreateDate());


        return projectRepository.save(projectEntity);
    }

    public void removeProjectFromUser(Long id_p, Long id) {
        ProjectEntity projectEntity = projectRepository.findByIdAndUserId(id_p, id)
                .orElseThrow(() -> new RuntimeException("Project not found or not owned by the user"));
        projectRepository.delete(projectEntity);
    }

    public ProjectEntity transferProjectToAnotherUser(Long id_p, Long newOwnerId) {
        ProjectEntity projectEntity = projectRepository.findById(id_p)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        UserEntity newOwner = userRepository.findById(newOwnerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return projectRepository.save(projectEntity);
    }


   // public List<ProjectEntity> searchProjects(String keyword) {return projectRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);}




}
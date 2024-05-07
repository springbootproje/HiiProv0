package com.java.app.ws.service;

import com.java.app.ws.dto.*;
import com.java.app.ws.repository.ProjectRepository;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository =userRepository;
    }





    @Transactional

    @Override


    public ProjectEntity createProject(String title, String description, Long creatorUserId) {
        UserEntity creator = userRepository.findById(creatorUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + creatorUserId));

        ProjectEntity project = new ProjectEntity();
        project.setTitle(title);
        project.setDescription(description);
        project.setCreateDate(LocalDate.now()); // Set the current date
        project.setCreator(creator); // Set the creator

        return projectRepository.save(project);
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




    public ProjectEntity updateProject(Long id, ProjectEntity projectDetails) {
        //  existing project and update its details
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + id));

        projectEntity.setTitle(projectDetails.getTitle());
        projectEntity.setDescription(projectDetails.getDescription());
        // Optionally allow updating the creation date or other fields
        // Do not update create datebut you can add a field called update_date that you automatically update at this moment


        return projectRepository.save(projectEntity);
    }

}
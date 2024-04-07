package com.java.app.ws.service;

import com.java.app.ws.repository.ProjectRepository;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import com.java.app.ws.dto.ProjectDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    ;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository =userRepository;
    }


    public ProjectEntity createProject(ProjectDto projectDto) {
        ProjectEntity projectEntity = new ProjectEntity();
        BeanUtils.copyProperties(projectDto,projectEntity);
        // Check if the creation date is not set
        if (projectEntity.getCreateDate() == null) {
            // Set the creation date to the current date
            projectEntity.setCreateDate(LocalDate.now());
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(projectDto.getUserId());
        projectEntity.setUser(userEntity);
        return projectRepository.save(projectEntity);
    }

    public ProjectDto getProjectById(Long id) {
        // Fetch a project by its ID or throw an exception if not found
        Optional<ProjectEntity> projectEntityOptional= projectRepository.findById(id);
        if(projectEntityOptional.isPresent()){
            ProjectEntity projectEntity = projectEntityOptional.get();
            ProjectDto projectDto = new ProjectDto();
            BeanUtils.copyProperties(projectEntity, projectDto);
            // if you want to add userId
            projectDto.setUserId(projectEntity.getUser().getId());
            return projectDto;
        }

        return null;
    }


    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
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


    public void deleteProject(Long id) {
        // Delete a projet par ID
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + id));
        projectRepository.delete(project);
    }

    public List<ProjectEntity> searchByTitle(String title) {
        // Search project par titre
        return projectRepository.findByTitleContaining(title);
    }

    public List<ProjectEntity> findAllProjectsByUserId(Long userId) {
        return projectRepository.findByUserId(userId);
    }

    public ProjectEntity addProjectForUser(ProjectEntity project, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        project.setUser(user);
        return projectRepository.save(project);
    }
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

    public List<ProjectEntity> findByCreationDateRange(LocalDate start, LocalDate end) {
        return projectRepository.findByCreateDateBetween(start, end);
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
        projectEntity.setUser(newOwner);
        return projectRepository.save(projectEntity);
    }


    public List<ProjectEntity> searchProjects(String keyword) {
        return projectRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
    }
    public List<ProjectEntity> getProjectsByDateRange(LocalDate start, LocalDate end) {
        return projectRepository.findByCreateDateBetween(start, end);
    }
    public UserEntity getUserByProject(Long id_p) {

        ProjectEntity projectEntity = projectRepository.findById(id_p)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id_p));


        return projectEntity.getUser();
    }


}
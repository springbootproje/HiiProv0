package com.java.app.ws.Service;

import com.java.app.ws.Repository.ProjectRepository;
import com.java.app.ws.Repository.UserRepository;
import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

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


    public ProjectEntity createProject(ProjectEntity projectEntity) {
        // Check if the creation date is not set
        if (projectEntity.getCreateDate() == null) {
            // Set the creation date to the current date
            projectEntity.setCreateDate(LocalDate.now());
        }
        return projectRepository.save(projectEntity);
    }

    public ProjectEntity getProjectById(Long id) {
        // Fetch a project by its ID or throw an exception if not found
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + id));
    }


    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }


    public ProjectEntity updateProject(Long id, ProjectEntity projectDetails) {
        //  existing project and update its details
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + id));

        project.setProject_title(projectDetails.getProject_title());
        project.setDescription(projectDetails.getDescription());
        // Optionally allow updating the creation date or other fields

        return projectRepository.save(project);
    }


    public void deleteProject(Long id) {
        // Delete a projet par ID
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + id));
        projectRepository.delete(project);
    }

    public List<ProjectEntity> searchByTitle(String title) {
        // Search project par titre
        return projectRepository.findByProjectTitleContaining(title);
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
        ProjectEntity project = projectRepository.findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new RuntimeException("Project not found or not owned by user"));


        project.setProject_title(projectDetails.getProject_title());
        project.setDescription(projectDetails.getDescription());
        project.setCreateDate(projectDetails.getCreateDate());


        return projectRepository.save(project);
    }

    public List<ProjectEntity> findByCreationDateRange(LocalDate start, LocalDate end) {
        return projectRepository.findByCreateDateBetween(start, end);
    }

    public void removeProjectFromUser(Long id_p, Long id) {
        ProjectEntity project = projectRepository.findByIdAndUserId(id_p, id)
                .orElseThrow(() -> new RuntimeException("Project not found or not owned by the user"));
        projectRepository.delete(project);
    }

    public ProjectEntity transferProjectToAnotherUser(Long id_p, Long newOwnerId) {
        ProjectEntity project = projectRepository.findById(id_p)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        UserEntity newOwner = userRepository.findById(newOwnerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        project.setUser(newOwner);
        return projectRepository.save(project);
    }


    public List<ProjectEntity> searchProjects(String keyword) {
        return projectRepository.findByProjectTitleContainingOrDescriptionContaining(keyword, keyword);
    }
    public List<ProjectEntity> getProjectsByDateRange(LocalDate start, LocalDate end) {
        return projectRepository.findByCreateDateBetween(start, end);
    }
    public UserEntity getUserByProject(Long projectId) {

        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));


        return project.getUser();
    }


}
package com.java.app.ws.controller;


import com.java.app.ws.ApiResponse;
import com.java.app.ws.dto.*;
import com.java.app.ws.entity.UserEntity;
import com.java.app.ws.repository.ProjectRepository;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.security.JWTGenerator;
import com.java.app.ws.service.ProjectServiceImpl;
import com.java.app.ws.entity.ProjectEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {


    private final ProjectRepository projectRepository;
    private final ProjectServiceImpl projectServiceImpl;
     @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectServiceImpl projectServiceImpl) {
        this.projectRepository = projectRepository;
        this.projectServiceImpl = projectServiceImpl;
    }


    @PostMapping("/new")
    public ResponseEntity<?> createProject(@RequestBody ProjectCreationDto projectRequest,
                                           @RequestHeader("Authorization") String token) {
        try {
            String jwt = token.substring(7); // Remove "Bearer " prefix
            String username = jwtGenerator.getUsernameFromJWT(jwt); // Extract username from JWT

            // Fetch user ID based on username
            UserEntity user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
            Long creatorUserId = user.getId();

            ProjectEntity project =  projectServiceImpl.createProject(
                    projectRequest.getTitle(),
                    projectRequest.getDescription(),
                    creatorUserId,
                    projectRequest.getMemberIds()
            );
            return ResponseEntity.ok(project);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
    @GetMapping("/list")//method tested
    public ResponseEntity<List<ProjectSummaryDto>> getAllProjects() {
        List<ProjectSummaryDto> projects = projectServiceImpl.getAllProjectSummaries();
        return ResponseEntity.ok(projects); //done
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProjectSummaryDto> getProjectById(@PathVariable("id") Long projectId) {
        ProjectSummaryDto project = projectServiceImpl.getProjectById(projectId);
        return ResponseEntity.ok(project);
    }


    @GetMapping("/search") //tested
    public ResponseEntity<List<ProjectSummaryDto>> searchProjectsByTitle(@RequestParam("title") String title) {
        List<ProjectSummaryDto> projects = projectServiceImpl.searchByTitle(title);
        return ResponseEntity.ok(projects); //id
    }


    @DeleteMapping("/delete/{id}") //method tested
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable("id") Long projectId) {
        projectServiceImpl.deleteProject(projectId);
        ApiResponse response = new ApiResponse("Project deleted successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date")
    public ResponseEntity<List<ProjectDto>> getProjectsFromStartDate(
            @RequestParam("start")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        List<ProjectDto> projects = projectServiceImpl.getProjectsFromStartDate(startDate);
        return ResponseEntity.ok(projects);
    }






    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectEntity> updateProject(@PathVariable("id") Long projectId, @RequestBody ProjectEntity projectEntity) {
        ProjectEntity updatedProject = projectServiceImpl.updateProject(projectId, projectEntity);
        return ResponseEntity.ok(updatedProject);
    }





























    //@DeleteMapping("/{projectId}/remove-from-user/{userId}")
   // public ResponseEntity<Void> removeProjectFromUser(
         //   @PathVariable Long projectId,
          //  @PathVariable Long userId) {
       // projectServiceImpl.removeProjectFromUser(projectId, userId);
      //  return ResponseEntity.ok().build();
   // }

    // @GetMapping("/searchText")
    // public ResponseEntity<List<ProjectEntity>> searchProjects(@RequestParam String keyword) {
    // List<ProjectEntity> projects = projectServiceImpl.searchProjects(keyword);
    // return ResponseEntity.ok(projects);
    // }
    //@PutMapping("/{projectId}/transfer")
   // public ResponseEntity<ProjectEntity> transferProjectToAnotherUser(
            //@PathVariable Long projectId,
           // @RequestParam Long newOwnerId) {
       // ProjectEntity updatedProject = projectServiceImpl.transferProjectToAnotherUser(projectId, newOwnerId);
        //return ResponseEntity.ok(updatedProject);
   // }

}

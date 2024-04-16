package com.java.app.ws.controller;


import com.java.app.ws.ApiResponse;
import com.java.app.ws.dto.*;
import com.java.app.ws.repository.ProjectRepository;

import com.java.app.ws.service.Impl.ProjectServiceImpl;
import com.java.app.ws.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {


    private final ProjectRepository projectRepository;
    private final ProjectServiceImpl projectServiceImpl;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectServiceImpl projectServiceImpl) {
        this.projectRepository = projectRepository;
        this.projectServiceImpl = projectServiceImpl;
    }

    @PostMapping (path="/new")  //methos tested
    public ResponseEntity<?> createProject(@RequestBody ProjectCreationDto projectRequest) {
        projectServiceImpl.createProject(
                projectRequest.getTitle(),
                projectRequest.getDescription(),
                projectRequest.getCreatorUserId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("Projet ajouté avec succès");
    }
    @GetMapping("/list")//method tested
    public ResponseEntity<List<ProjectSummaryDto>> getAllProjects() {
        List<ProjectSummaryDto> projects = projectServiceImpl.getAllProjectSummaries();
        return ResponseEntity.ok(projects); //done
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


    //@GetMapping("/{id}")
    // public ResponseEntity<ProjectDto> getProjectById(@PathVariable("id") Long projectId) {
    //ProjectDto projectDto = projectServiceImpl.getProjectById(projectId);

    // return ResponseEntity.ok(projectDto);
    // }



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

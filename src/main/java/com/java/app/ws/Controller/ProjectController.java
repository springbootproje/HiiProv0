package com.java.app.ws.Controller;


import com.java.app.ws.Repository.ProjectRepository;

import com.java.app.ws.Service.ProjectService;
import com.java.app.ws.Entity.ProjectEntity;
import com.java.app.ws.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {


    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }


    @GetMapping("/list")
    public ResponseEntity<List<ProjectEntity>> getAllProjects() {
        List<ProjectEntity> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects); //done
    }
    @GetMapping(path = "/user_projects/{user_id}")
    public List<ProjectEntity> getProjectByUser(@PathVariable("user_id") Long userId){
            return projectService.findAllProjectsByUserId(userId); //find all PROJECT by a specefic user id

        }
    @GetMapping("/{id}") // List project by its ID
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable("id") Long projectId) {
        ProjectDto projectDto = projectService.getProjectById(projectId);
        return ResponseEntity.ok(projectDto);
    }//done




//    @GetMapping(path = "/listProjet{id}") //liste de projet d'un user
//    public List<ProjectEntity> getProjectrByUser(@PathVariable("id") Long id) {
//        return projectRepository.findByUserId(id); //user id in a project id_p
//        //product in events eventId
//    }



    @PostMapping (path="/new")  //CREATE PROJECT
    public ResponseEntity<ProjectEntity> createProject(@RequestBody ProjectDto project) {
        ProjectEntity newProject = projectService.createProject(project);
        return ResponseEntity.ok(newProject);
    } //done


    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectEntity> updateProject(@PathVariable("id") Long projectId, @RequestBody ProjectEntity projectEntity) {
        ProjectEntity updatedProject = projectService.updateProject(projectId, projectEntity);
        return ResponseEntity.ok(updatedProject);
    } //done

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok().build();
    }//done


    @GetMapping("/search")
    public ResponseEntity<List<ProjectEntity>> searchProjectsByTitle(@RequestParam("title") String title) {
        List<ProjectEntity> projects = projectService.searchByTitle(title);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/date")
    public ResponseEntity<List<ProjectEntity>> getProjectsByCreationDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ProjectEntity> projects = projectService.findByCreationDateRange(startDate, endDate);
        return ResponseEntity.ok(projects);
    }



    @GetMapping("/searchText")
    public ResponseEntity<List<ProjectEntity>> searchProjects(@RequestParam String keyword) {
        List<ProjectEntity> projects = projectService.searchProjects(keyword);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<ProjectEntity>> getProjectsByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<ProjectEntity> projects = projectService.getProjectsByDateRange(start, end);
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{projectId}/transfer")
    public ResponseEntity<ProjectEntity> transferProjectToAnotherUser(
            @PathVariable Long projectId,
            @RequestParam Long newOwnerId) {
        ProjectEntity updatedProject = projectService.transferProjectToAnotherUser(projectId, newOwnerId);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}/remove-from-user/{userId}")
    public ResponseEntity<Void> removeProjectFromUser(
            @PathVariable Long projectId,
            @PathVariable Long userId) {
        projectService.removeProjectFromUser(projectId, userId);
        return ResponseEntity.ok().build();
    }

}

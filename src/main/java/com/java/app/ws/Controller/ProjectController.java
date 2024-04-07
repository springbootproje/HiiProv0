package com.java.app.ws.Controller;


import com.java.app.ws.Repository.ProjectRepository;
import com.java.app.ws.Repository.UserRepository;

import com.java.app.ws.Service.ProjectService;
import com.java.app.ws.Entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController

public class ProjectController {




    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    private final ProjectService projectService;

@Autowired
    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectService = projectService;
    }



    @RequestMapping("/project")



    @GetMapping("/listProjects")
    public ResponseEntity<List<ProjectEntity>> getAllProjects() {
        List<ProjectEntity> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects); //done
    }
    @GetMapping(path = "/projectUser")
    public Optional<ProjectEntity> getProjectByUser(@RequestParam("id") Long id){
            return projectRepository.findById(id); //find all PROJECT by a specefic user id

        }
    @GetMapping("/{idP}") // List project by its ID
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable("idP") Long idP) {
        ProjectEntity projectEntity = projectService.getProjectById(idP);
        return ResponseEntity.ok(projectEntity);
    }//done




    @GetMapping(path = "/listProjet{id}") //liste de projet d'un user
    public List<ProjectEntity> getProjectrByUser(@PathVariable("id") Long id) {
        return projectRepository.findByUserId(id); //user id in a project id_p
        //product in events eventId
    }



    @PostMapping (path="/create_project")  //CREATE PROJECT
    public ResponseEntity<ProjectEntity> createProject(@RequestBody ProjectEntity project) {
        ProjectEntity newProject = projectService.createProject(project);
        return ResponseEntity.ok(newProject);
    } //done


    @PutMapping("/update{idP}")
    public ResponseEntity<ProjectEntity> updateProject(@PathVariable("id_p") Long id_p, @RequestBody ProjectEntity projectEntity) {
        ProjectEntity updatedProject = projectService.updateProject(id_p, projectEntity);
        return ResponseEntity.ok(updatedProject);
    } //done

    @DeleteMapping("/delete_project")
    public ResponseEntity<Void> deleteProject(@PathVariable("id_p") Long id_p) {
        projectService.deleteProject(id_p);
        return ResponseEntity.ok().build();
    }//done


    @GetMapping("/searchTitle")
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

package com.java.app.ws.controller;


import com.java.app.ws.Repository.ProjectRepository;
import com.java.app.ws.Repository.UserRepository;
import com.java.app.ws.request.Project;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController

public class ProjectController {




    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }



    @RequestMapping("project")
    @GetMapping("/listProjects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll(); //list of all projects
    }
    @GetMapping(path = "/projectUser")
    public Optional<Project> getProjectByUser(@RequestParam("id") int id){
            return projectRepository.findById(id); //find all PROJECT by a specefic user id

        }
    @GetMapping(path = "/project/{id}") //trouver un projet
        public Project getProjecttById(@PathVariable("id_p") int id_p){
            return projectRepository.findById( id_p).orElseThrow(() -> new NoSuchElementException("Project with id " + id_p + " not found")); //get project by it id
        }



    @GetMapping(path = "/listProjet{id}") //liste de projet d'un user
    public List<Project> getProjectrByUser(@RequestParam("id") int id) {
        return projectRepository.findById(id); //user id in a project id_p
        //product in events eventId
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse notFound(NoSuchElementException ex){
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage()); //if there no project wit this id
    }

    @PostMapping("/addProject")
    public String addProject(@RequestBody String project_title) {
        return project_title;
    }


    /*@PutMapping("{id_p}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails) {
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmailId(employeeDetails.getEmailId());

        employeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }
*/

    @PutMapping("/project/{id_p}")
    public void updateProject(@PathVariable("id_p") int id_p, @RequestBody Project project) {
        // Update the user details here
    }


    @DeleteMapping("/project/{id_p}")
    public void deleteProject(@PathVariable("id_p") int id_p) {
        // Delete the user in this method with the id.
    }
}

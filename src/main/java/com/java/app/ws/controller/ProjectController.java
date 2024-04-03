package com.java.app.ws.controller;


import com.java.app.ws.Repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("project")
public class ProjectController {


    @GetMapping("/students")
    public List<Project> getAllStudents() {
        return ProjectRepository.findAll();
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

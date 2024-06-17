package com.java.app.ws.controller;

import com.java.app.ws.Exception.ProjectNotFoundException;
import com.java.app.ws.Exception.UserAlreadyInProjectException;
import com.java.app.ws.Exception.UserNotFoundException;
import com.java.app.ws.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/participant")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @PostMapping("/addUserToProject")
    public ResponseEntity<?> addUserToProject(@RequestParam String email, @RequestParam Long projectId) {
        try {
            participantService.addUserToProject(email, projectId);
            return ResponseEntity.ok("User successfully added to the project.");
        } catch (UserNotFoundException | ProjectNotFoundException | UserAlreadyInProjectException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeUserFromProject(@RequestParam String email, @RequestParam Long projectId) {
        participantService.removeUserFromProject(email, projectId);
        return ResponseEntity.ok("User removed from project successfully.");
    }
}








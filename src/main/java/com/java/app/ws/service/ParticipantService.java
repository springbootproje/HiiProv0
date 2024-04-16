package com.java.app.ws.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ParticipantService {

     void addUserToProject( String email, Long projectId);
    void removeUserFromProject(String email, Long projectId);
}

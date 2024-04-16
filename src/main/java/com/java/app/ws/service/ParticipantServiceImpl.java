package com.java.app.ws.service;

import com.java.app.ws.Exception.ProjectNotFoundException;
import com.java.app.ws.Exception.UserAlreadyInProjectException;
import com.java.app.ws.Exception.UserNotFoundException;
import com.java.app.ws.entity.ParticipantEntity;
import com.java.app.ws.entity.ParticipantId;
import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import com.java.app.ws.repository.ParticipantRepo;
import com.java.app.ws.repository.ProjectRepository;
import com.java.app.ws.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ParticipantRepo participantRepository;
    @Override
    public void addUserToProject(String email, Long projectId) {
        // Vérifier si l'utilisateur existe
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));

        // Vérifier si le projet existe
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with ID " + projectId + " not found."));

        // Créer une nouvelle instance de l'ID composite
        ParticipantId participantId = new ParticipantId();
        participantId.setUserId(user.getId());
        participantId.setProjectId(project.getId());

        // Vérifier si l'utilisateur est déjà associé au projet
        Optional<ParticipantEntity> existingParticipant = participantRepository.findById(participantId);
        if (existingParticipant.isPresent()) {
            throw new UserAlreadyInProjectException("User with email " + email + " is already in project with ID " + projectId + ".");
        }

        // Ajouter l'utilisateur au projet
        ParticipantEntity participant = new ParticipantEntity();
        participant.setId(participantId);
        participant.setUser(user);
        participant.setProject(project);

        participantRepository.save(participant);
    }

    @Override
    public void removeUserFromProject(String email, Long projectId) {
        // Vérifier si l'utilisateur existe
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));

        // Vérifier si le projet existe
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with ID " + projectId + " not found."));

        // Rechercher et supprimer le participant
        ParticipantEntity participant = participantRepository.findByUserAndProject(user, project);
        if (participant != null) {
            participantRepository.delete(participant);
        }
    }


}


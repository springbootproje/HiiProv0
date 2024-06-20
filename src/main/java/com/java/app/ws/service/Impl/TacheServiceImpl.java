package com.java.app.ws.service.Impl;

import com.java.app.ws.dto.TacheCreationDto;
import com.java.app.ws.dto.TacheDto;
import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.TacheEntity;
import com.java.app.ws.entity.UserEntity;
import com.java.app.ws.repository.ProjectRepository;
import com.java.app.ws.repository.TacheRepository;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.service.TacheService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TacheServiceImpl implements TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public TacheDto createTache(TacheCreationDto tacheCreationDto) {
        TacheEntity tacheEntity = new TacheEntity();
        tacheEntity.setTitle(tacheCreationDto.getTitle());
        tacheEntity.setDescription(tacheCreationDto.getDescription());
        tacheEntity.setStatut(tacheCreationDto.getStatus());

        // Set the user if provided
        if (tacheCreationDto.getUserId() != null) {
            UserEntity user = userRepository.findById(tacheCreationDto.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + tacheCreationDto.getUserId()));
            tacheEntity.setUser(user);
        }

        // Set the project and add the task to the project's task list if provided
        if (tacheCreationDto.getProjectId() != null) {
            ProjectEntity project = projectRepository.findById(tacheCreationDto.getProjectId())
                    .orElseThrow(() -> new NoSuchElementException("Project not found with ID: " + tacheCreationDto.getProjectId()));
            tacheEntity.setProject(project);

            // Ensure the project has an initialized task list
            if (project.getTasks() == null) {
                project.setTasks(new ArrayList<>());
            }
            project.getTasks().add(tacheEntity);
        }

        // Save the task entity
        TacheEntity newTache = tacheRepository.save(tacheEntity);

        // Convert to TacheDto
        TacheDto tacheDto = new TacheDto();
        tacheDto.setId(newTache.getId());
        tacheDto.setTitle(newTache.getTitle());
        tacheDto.setDescription(newTache.getDescription());
        tacheDto.setStatus(newTache.getStatut());
        tacheDto.setUserId(newTache.getUser() != null ? newTache.getUser().getId() : null);
        tacheDto.setProjectId(newTache.getProject().getId());

        return tacheDto;
    }

    @Override
    public TacheDto updateTacheStatus(Long id, String status) {
        TacheEntity tacheEntity = tacheRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tâche non trouvée avec l'ID : " + id));

        // Mettre à jour le statut de la tâche
        tacheEntity.setStatut(status);

        // Enregistrer la mise à jour dans la base de données
        TacheEntity updatedTacheEntity = tacheRepository.save(tacheEntity);

        // Convertir l'entité mise à jour en DTO
        TacheDto tacheDto = new TacheDto();
        BeanUtils.copyProperties(updatedTacheEntity, tacheDto);

        return tacheDto;
    }

    @Override
    public void deleteTache(Long id) {
        if (tacheRepository.existsById(id)) {
            tacheRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("La tâche avec l'ID " + id + " n'existe pas");
        }
    }

    @Override
    public List<TacheDto> getAllTaches() {
        // Implémentez cette méthode si nécessaire
        throw new UnsupportedOperationException("Unimplemented method 'getAllTaches'");
    }

    @Override
    public TacheDto updateTache(Long id, TacheDto tacheDto) {
        // Implémentation de la mise à jour de la tâche
        TacheEntity tacheEntity = tacheRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tâche non trouvée avec l'ID : " + id));

        BeanUtils.copyProperties(tacheDto, tacheEntity, "id"); // Ignore 'id' lors de la copie

        TacheEntity updatedTache = tacheRepository.save(tacheEntity);

        TacheDto updatedTacheDto = new TacheDto();
        BeanUtils.copyProperties(updatedTache, updatedTacheDto);

        return updatedTacheDto;
    }

    @Override
    public TacheDto getTacheById(Long id) {
        TacheEntity tacheEntity = tacheRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tâche non trouvée avec l'ID : " + id));

        TacheDto tacheDto = new TacheDto();
        BeanUtils.copyProperties(tacheEntity, tacheDto);

        return tacheDto;
    }

}

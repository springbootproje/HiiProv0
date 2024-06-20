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
import java.util.stream.Collectors;

@Service
public class TacheServiceImpl implements TacheService {
     @Autowired
     TacheRepository tacheRepository;

     @Autowired
     UserRepository userRepository;

     @Autowired
     ProjectRepository projectRepository;


    @Override
public TacheDto createTache(TacheCreationDto tacheCreationDto) {
    TacheEntity tacheEntity = new TacheEntity();
    tacheEntity.setTitle(tacheCreationDto.getTitle());
    tacheEntity.setDescription(tacheCreationDto.getDescription());
    tacheEntity.setStatut(tacheCreationDto.getStatus());
    tacheEntity.setDateCreation(tacheCreationDto.getDateCreation());


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
    tacheDto.setDateCreation(newTache.getDateCreation());


    return tacheDto;
}




    @Override
    public TacheDto updateTache(Long id, TacheDto tacheDto) {
        Optional<TacheEntity> optionalTache = tacheRepository.findById(id);
        if (optionalTache.isPresent()) {
            TacheEntity tacheEntity = optionalTache.get();
            BeanUtils.copyProperties(tacheDto, tacheEntity, "id"); // Assurez-vous que "id" est exclu pour ne pas écraser l'ID
            tacheRepository.save(tacheEntity);
            BeanUtils.copyProperties(tacheEntity, tacheDto); // Assurez-vous de copier les propriétés mises à jour dans l'objet DTO
            return tacheDto;
        } else {
            throw new EntityNotFoundException("La tâche avec l'ID " + id + " n'existe pas");
        }
    }






    @Override
    public void deleteTache(Long id) {
        Optional<TacheEntity> optionalTache = tacheRepository.findById(id);
        if (optionalTache.isPresent()) {
            tacheRepository.deleteById(id);
        } else {
            // Gérer le cas où la tâche n'existe pas
            throw new EntityNotFoundException("La tâche avec l'ID " + id + " n'existe pas");
        }
    }




    @Override
    public List<TacheDto> getAllTaches() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTaches'");
    }




    @Override
    public TacheDto getTacheById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTacheById'");
    }
}

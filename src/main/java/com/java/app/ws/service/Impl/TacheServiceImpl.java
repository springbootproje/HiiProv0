package com.java.app.ws.service.Impl;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TacheServiceImpl implements TacheService {
     @Autowired
     TacheRepository tacheRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public TacheDto createTache(TacheDto tacheDto) {
        // Créer une nouvelle entité TacheEntity
        TacheEntity tacheEntity = new TacheEntity();

        // Copier les propriétés de tacheDto vers tacheEntity
        BeanUtils.copyProperties(tacheDto, tacheEntity);

        // Récupérer l'utilisateur associé à la tâche
        UserEntity userEntity = userRepository.findById(tacheDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID: " + tacheDto.getUserId()));

        // Récupérer le projet associé à la tâche
        ProjectEntity projectEntity = projectRepository.findById(tacheDto.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID: " + tacheDto.getProjectId()));

        // Associer l'utilisateur et le projet à la tâche
        tacheEntity.setUser(userEntity);
        tacheEntity.setProject(projectEntity);

        // Sauvegarder la tâche dans la base de données
        TacheEntity newTacheEntity = tacheRepository.save(tacheEntity);

        // Créer un DTO à partir de la nouvelle entité de tâche
        TacheDto newTacheDto = new TacheDto();
        BeanUtils.copyProperties(newTacheEntity, newTacheDto);

        return newTacheDto;
    }


    @Override
    public List<TacheDto> getAllTaches() {
        List<TacheEntity> tacheEntities = (List<TacheEntity>) tacheRepository.findAll();
        return tacheEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TacheDto convertToDto(TacheEntity tacheEntity )
    {
        TacheDto tacheDto = new TacheDto();
        BeanUtils.copyProperties(tacheEntity, tacheDto);
        if (tacheEntity.getUser() != null) {
            tacheDto.setUserId(tacheEntity.getUser().getId());
        }

        if (tacheEntity.getProject() != null) {
            tacheDto.setProjectId(tacheEntity.getProject().getId());
        }
        return tacheDto ;
    }

    @Override
    public TacheDto getTacheById(Long id) {
        Optional<TacheEntity> optionalTache = tacheRepository.findById(id);
        if (optionalTache.isPresent()) {
            TacheEntity tacheEntity = optionalTache.get();
            TacheDto tacheDto = convertToDto(tacheEntity);
            tacheDto.setUserId(tacheEntity.getUser().getId()); // Supposons que User soit la relation avec l'utilisateur
            tacheDto.setProjectId(tacheEntity.getProject().getId()); // Supposons que Projet soit la relation avec le projet
            return tacheDto;
        } else {
            return null; // Ou lancez une exception EntityNotFoundException
        }
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
    //"Nv methode  get tache par id user"
    @Override
    public List<TacheDto> getTachesByUserId(Long userId) {
        List<TacheEntity> tacheEntities = tacheRepository.findByUserId(userId);
        return tacheEntities.stream()
                .map(this::convertToDtoInternal) // Utilisation de la méthode convertToDtoInternal
                .collect(Collectors.toList());
    }

    private TacheDto convertToDtoInternal(TacheEntity tacheEntity) {
        TacheDto tacheDto = new TacheDto();
        BeanUtils.copyProperties(tacheEntity, tacheDto);
        if (tacheEntity.getUser() != null) {
            tacheDto.setUserId(tacheEntity.getUser().getId());
        }

        if (tacheEntity.getProject() != null) {
            tacheDto.setProjectId(tacheEntity.getProject().getId());
        }
        return tacheDto;
    }
    //nv methode de get tache by id-project
    @Override
    public List<TacheDto> getTachesByProjectId(Long projectId) {
        List<TacheEntity> tacheEntities = tacheRepository.findByProjectId(projectId);
        return tacheEntities.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private TacheDto convert(TacheEntity tacheEntity) {
        TacheDto tacheDto = new TacheDto();
        BeanUtils.copyProperties(tacheEntity, tacheDto);

        // Extraire userId et projectId des entités liées
        if (tacheEntity.getUser() != null) {
            tacheDto.setUserId(tacheEntity.getUser().getId());
        }

        if (tacheEntity.getProject() != null) {
            tacheDto.setProjectId(tacheEntity.getProject().getId());
        }

        return tacheDto;
    }

}


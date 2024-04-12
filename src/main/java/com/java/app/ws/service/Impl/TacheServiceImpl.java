package com.java.app.ws.service.Impl;
import com.java.app.ws.dto.TacheDto;
import com.java.app.ws.entity.TacheEntity;
import com.java.app.ws.repository.TacheRepository;
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
    @Override
    public TacheDto createTache(TacheDto tache) {
        TacheEntity tacheEntity = new TacheEntity();
        BeanUtils.copyProperties(tache,tacheEntity);

         TacheEntity newTache = tacheRepository.save(tacheEntity);
         TacheDto tacheDto =new TacheDto();
         BeanUtils.copyProperties(newTache,tacheDto);
        return tacheDto;
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
        return tacheDto ;
    }

    @Override
    public TacheDto getTacheById(Long id) {
        Optional<TacheEntity> optionalTache = tacheRepository.findById(id);
        if (optionalTache.isPresent()) {
            TacheEntity tacheEntity = optionalTache.get();
            return convertToDto(tacheEntity);
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
}

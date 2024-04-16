package com.java.app.ws.service;

import com.java.app.ws.dto.TacheDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TacheService {
     TacheDto createTache(TacheDto tacheDto);

     List<TacheDto> getAllTaches();

     TacheDto updateTache(Long id, TacheDto tacheDto);

     void deleteTache(Long id);

     TacheDto getTacheById(Long id);

//Nv methode get tache by id-user
     List<TacheDto> getTachesByUserId(Long userId);

//nv ethode get tache by id-project
     List<TacheDto> getTachesByProjectId(Long projectId);
}

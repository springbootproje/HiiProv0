package com.java.app.ws.service;

import com.java.app.ws.dto.TacheCreationDto;
import com.java.app.ws.dto.TacheDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TacheService {
     TacheDto createTache(TacheCreationDto tacheCreationDto);

     List<TacheDto> getAllTaches();

     TacheDto updateTache(Long id, TacheDto tacheDto);

     void deleteTache(Long id);

     TacheDto getTacheById(Long id);
}

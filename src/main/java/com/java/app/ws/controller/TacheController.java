package com.java.app.ws.controller;

import com.java.app.ws.Request.TacheRequest;
import com.java.app.ws.Response.TacheResponse;

import com.java.app.ws.dto.TacheDto;
import com.java.app.ws.service.TacheService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tache")//localhost:8080/tache
@RestController
public class TacheController {

	@Autowired
	TacheService tacheService ;


	@PostMapping(value="/create")

	public TacheResponse createTache(@RequestBody TacheRequest tacheRequest)
	{

		TacheDto tacheDto =new TacheDto();

		BeanUtils.copyProperties(tacheRequest,tacheDto);

		 TacheDto createTache = tacheService.createTache(tacheDto);

		TacheResponse tacheResponse;

        tacheResponse = new TacheResponse();

        BeanUtils.copyProperties(createTache,tacheResponse);

		return tacheResponse;

	}
	@GetMapping(value="/get")
	public List<TacheDto> getAllTaches() {
		return tacheService.getAllTaches();

	}
	@GetMapping(value="/{id}")
	public ResponseEntity<TacheDto> getTacheById(@PathVariable Long id) {
		TacheDto tacheDto = tacheService.getTacheById(id);
		if (tacheDto != null) {
			return ResponseEntity.ok(tacheDto);
		} else {
			return ResponseEntity.notFound().build(); // Tâche non trouvée
		}
	}
	@PutMapping(value="/update/{id}")
	public TacheDto updateTache(@PathVariable Long id, @RequestBody TacheRequest tacheRequest) {
		TacheDto tacheDto = new TacheDto();
		BeanUtils.copyProperties(tacheRequest, tacheDto,"id");// id fixe
		return tacheService.updateTache(id, tacheDto);
	}

	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<String> deleteTache(@PathVariable Long id) {
		tacheService.deleteTache(id);
		return ResponseEntity.ok("Tâche avec l'ID " + id+ " supprimée avec succès");
	}

}

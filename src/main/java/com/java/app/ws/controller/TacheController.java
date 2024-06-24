package com.java.app.ws.controller;

import com.java.app.ws.Response.ApiResponse;
import com.java.app.ws.dto.TacheCreationDto;
import com.java.app.ws.dto.TacheDto;
import com.java.app.ws.entity.UserEntity;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.security.JWTGenerator;
import com.java.app.ws.service.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/tache")//localhost:8080/tache
@RestController
public class TacheController {

	@Autowired
	TacheService tacheService ;
	@Autowired
	private JWTGenerator jwtGenerator;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/create")
	public ResponseEntity<?> createTache(@RequestBody TacheCreationDto tacheCreationDto, @RequestHeader("Authorization") String token) {
		try {
			String jwt = token.substring(7); // Remove "Bearer " prefix
			String username = jwtGenerator.getUsernameFromJWT(jwt); // Extract username from JWT
			UserEntity user = userRepository.findByEmail(username)
					.orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
			tacheCreationDto.setUserId(user.getId()); // Set the user ID to the creator

			TacheDto createdTache = tacheService.createTache(tacheCreationDto);
			return ResponseEntity.ok(createdTache);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
		}
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
	public ResponseEntity<?> updateTacheStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
		try {
			String newStatus = statusUpdate.get("status");
			TacheDto updatedTache = tacheService.updateTacheStatus(id, newStatus);
			return ResponseEntity.ok(updatedTache);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tâche non trouvée avec l'ID : " + id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du statut de la tâche : " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteTache(@PathVariable Long id) {
		try {
			tacheService.deleteTache(id);
			return ResponseEntity.ok(new ApiResponse("deleted"));
		} catch (NoSuchElementException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
		} catch (Exception e) {
				return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
		}
	}



}

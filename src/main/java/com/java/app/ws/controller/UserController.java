package com.java.app.ws.controller;

import com.java.app.ws.dto.CreateUserDto;
import com.java.app.ws.dto.UserDto;
import com.java.app.ws.service.ProjectService;
import com.java.app.ws.service.UserService;
import com.java.app.ws.entity.UserEntity;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/user")
public class UserController {
	/*@Autowired
	UserService userService;*/


	private final UserService userService;
	private final ProjectService projectService;

	public UserController(UserService userService, ProjectService projectService) {
		this.userService =userService;
        this.projectService = projectService;
    }

	@PostMapping(path="/new")
	public ResponseEntity<CreateUserDto> createUser(@RequestBody CreateUserDto user) {
		CreateUserDto newUser = userService.createUser(user);
		return ResponseEntity.ok(newUser);
	}

	@GetMapping(path="/list")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
		UserEntity user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity userDetails) {
		// Assume the incoming userDetails object does not contain password information
		UserEntity updatedUser = userService.updateUser(id, userDetails);
		return ResponseEntity.ok(updatedUser);
	}

	@PostMapping("/{id}/change-password")
	public ResponseEntity<?> changePassword(@PathVariable Long id,
											@RequestParam String currentPassword,
											@RequestParam String newPassword) {
		// Ensure this endpoint is secure and accessible only by the user whose ID is specified
		boolean success = userService.updatePassword(id, currentPassword, newPassword);
		if (success) {
			return ResponseEntity.ok().build();
		} else {
			// Handle failure scenario, possibly returning a different status or error message
			return ResponseEntity.badRequest().body("Password update failed");
		}
	}


	@DeleteMapping("/delete{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}


	@GetMapping("/email/{email}")
	public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) {
		UserEntity user = userService.getUserByEmail(email);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/{projectId}/user")
	public ResponseEntity<UserEntity> getUserByProject(@PathVariable Long projectId) {
		UserEntity user = projectService.getUserByProject(projectId);
		return ResponseEntity.ok(user);
	}







	}
	
	



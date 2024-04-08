package com.java.app.ws.controller;

import com.java.app.ws.dto.CreateUserDto;
import com.java.app.ws.dto.PasswordChangeDto;
import com.java.app.ws.dto.UserDto;
import com.java.app.ws.service.ProjectService;
import com.java.app.ws.service.UserService;

import org.springframework.http.HttpStatus;
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
		this.userService = userService;
        this.projectService = projectService;
    }

	@PostMapping(path="/new") //method tested
	public ResponseEntity<CreateUserDto> createUser(@RequestBody CreateUserDto user) {
		CreateUserDto newUser = userService.createUser(user);
		return ResponseEntity.ok(newUser); //method tested
	}

	@GetMapping(path="/list") //method tested
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/get/{id}") //method tested
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
		UserDto userDto = userService.getUserById(id);
		return ResponseEntity.ok(userDto);
	}

	@PutMapping("/update/{id}") //method tested
	public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDetails) {
		// Assume the incoming userDetails object does not contain password information
		UserDto updatedUserDto = userService.updateUser(id, userDetails);
		return ResponseEntity.ok(updatedUserDto);
	}


	@PostMapping("/{id}/change-password") //method tested
	public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordChangeDto passwordChangeDto) {
		try {
			userService.updatePassword(id, passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
			return ResponseEntity.ok("Password updated successfully");
		} catch (RuntimeException ex) {
			// Dans un vrai scénario, tu devrais gérer cela globalement avec @ControllerAdvice
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(ex.getMessage());
		}
	}


	@DeleteMapping("/delete{id}")//method tsted
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("User deleted successfully");
	}


	//@GetMapping("/users/email/{email}")
	//public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
		//UserDto userDto = userService.getUserByEmail(email);
		//return ResponseEntity.ok(userDto);
	//}



	//@GetMapping("/{projectId}/users")
	//public ResponseEntity<List<UserDto>> getUsersByProject(@PathVariable Long projectId) {
		//List<UserDto> users = projectService.getUsersByProject(projectId);
		//return ResponseEntity.ok(users);
	}














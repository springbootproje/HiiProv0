package com.java.app.ws.controller;

import com.java.app.ws.Response.LoginResponse;
import com.java.app.ws.dto.*;
import com.java.app.ws.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/new") //mzthod tested
	public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
		UserDto newUserDto = userService.createUser(createUserDto);
		return ResponseEntity.ok("User created successfully with ID: " + newUserDto.getId() + ", Name: " + newUserDto.getFirstName() + " " + newUserDto.getLastName() + ", Email: " + newUserDto.getEmail());
	}
	@PostMapping(path = "/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO)
	{
		LoginResponse loginResponse = userService.loginUser(loginDTO);
		return ResponseEntity.ok(loginResponse);
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		// Assuming session management is enabled
		request.getSession().invalidate();

		// Redirect to the login page or home page after logout
		return "redirect:/login";  // Assuming '/login' is your login page URL
	}


	@GetMapping("/list") //method tested
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
	public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto userDetails) {
		UserDto updatedUser = userService.updateUser(id, userDetails);
		return ResponseEntity.ok(updatedUser);
	}


	@PostMapping("/{id}/change-password")//method tested
	public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordChangeDto passwordChangeDto) {
		try {
			boolean updateSuccess = userService.updatePassword(id, passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
			if (updateSuccess) {
				return ResponseEntity.ok("Password updated successfully");
			} else {
				return ResponseEntity.badRequest().body("Password update failed");
			}
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}") //method tested
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("User deleted successfully");
	}
}

package com.java.app.ws.controller;

import com.java.app.ws.Response.LoginResponse;
import com.java.app.ws.dto.*;
import com.java.app.ws.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(path = "/new") //mzthod tested
	public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
		UserDto newUserDto = userService.createUser(createUserDto);
		Map<String, String> response = new HashMap<>();
		response.put("message", "User created successfully");
		response.put("id", Long.toString(newUserDto.getId()));
		response.put("name", newUserDto.getFirstName() + " " + newUserDto.getLastName());
		response.put("email", newUserDto.getEmail());
		return ResponseEntity.ok(response);
	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO)
	{
		LoginResponse loginResponse = userService.loginUser(loginDTO);
		return ResponseEntity.ok(loginResponse);
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();

		return "redirect:/login";
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

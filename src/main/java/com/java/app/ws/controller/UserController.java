package com.java.app.ws.controller;

import com.java.app.ws.Response.ApiResponse;
import com.java.app.ws.Response.AuthResponseDTO;
import com.java.app.ws.dto.*;
import com.java.app.ws.entity.UserEntity;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.security.JWTGenerator;
import com.java.app.ws.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private JWTGenerator jwtGenerator;
	private UserRepository userRepository;
	@Autowired
	public UserController(UserService userService, AuthenticationManager authenticationManager,JWTGenerator jwtGenerator, UserRepository userRepository) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtGenerator = jwtGenerator;
		this.userRepository = userRepository;
	}




	@PostMapping("/new")
	public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
		try {
			UserDto newUserDto = userService.createUser(createUserDto);
			Map<String, String> response = new HashMap<>();
			response.put("message", "User created successfully");
			response.put("id", Long.toString(newUserDto.getId()));
			response.put("name", newUserDto.getFirstName() + " " + newUserDto.getLastName());
			response.put("email", newUserDto.getEmail());
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", "User already exists");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse); // Use 409 Conflict status code
		} catch (Exception e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", "An unexpected error occurred");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
	@PostMapping("/login")


	public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
		// Authenticate the user
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDTO.getEmail(),
						loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Generate JWT token
		String token = jwtGenerator.generateToken(authentication);

		// Extract the authenticated user's details from the Authentication object

		UserEntity userEntity = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));;


		// Get the user's role
		String role = userEntity.getRole();

		// Create the response DTO including the token and role
		AuthResponseDTO responseDTO = new AuthResponseDTO(token, role);

		// Return the response
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
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


	@PostMapping("/change-password")
	public ResponseEntity<ApiResponse> changePassword(@RequestBody PasswordChangeRequestDto passwordChangeRequestDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserEmail = authentication.getName();

		try {
			userService.changePassword(currentUserEmail, passwordChangeRequestDto.getCurrentPassword(), passwordChangeRequestDto.getNewPassword());
			return ResponseEntity.ok(new ApiResponse("Password changed successfully"));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
		}
	}
	@DeleteMapping("/delete/{id}") //method tested
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("User deleted successfully");
	}
}

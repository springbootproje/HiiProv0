package com.java.app.ws.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.app.ws.requests.UserRequest;
import com.java.app.ws.responses.UserResponses;
import com.java.app.ws.services.UserService;
import com.java.app.ws.shared.dto.UserDto;



@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	@GetMapping
	public  String GetUser() {
		return "get user was called";
		
	}
	
	@PostMapping
	public  UserResponses createUser(@RequestBody UserRequest userRequest) {
		UserDto userDto= new UserDto();
		BeanUtils.copyProperties(userRequest, userDto);
		 UserDto createUser= userService.createUser(userDto);
		 UserResponses userResponse= new UserResponses();
			BeanUtils.copyProperties(createUser, userResponse);
			return userResponse;
	}
	@PutMapping
	public  String updateUser() {
		return "update user was called";
		
	}
	@DeleteMapping
	public  String deleteUser() {
		return "delete user was called";
		
	}
	
	

}

package com.java.app.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.java.app.ws.requests.UserRequest;
import com.java.app.ws.responses.UserResponses;
import com.java.app.ws.services.UserService;




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
		/*UserDto userDto= new UserDto();
		BeanUtils.copyProperties(userRequest, userDto);
		 UserDto createUser= userService.createUser(userDto);
		 UserResponses userResponse= new UserResponses();
			BeanUtils.copyProperties(createUser, userResponse);*/
			return userResponse;
	}
	@PutMapping("/users")
	public void updateUser(@PathVariable("id") int id, @RequestParam("userNamefirstName") String firstName) {
		// inside this method we have to update the user record
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable("id") int id) {
		// Delete the user in this method with the id.
	}
	}
	
	



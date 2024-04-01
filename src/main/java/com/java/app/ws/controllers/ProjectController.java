package com.java.app.ws.controllers;

import com.java.app.ws.requests.UserRequest;
import com.java.app.ws.responses.UserResponses;
import com.java.app.ws.services.UserService;
import com.java.app.ws.shared.dto.ProjectDto;
import com.java.app.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * GET: /project/list => liste des projets
 * POST: /project/add => ajout d'un projet
 */

@RestController
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	UserService userService;
	@GetMapping(value = "/list")
	public  String GetUser() {
		// create ProjectService
		return "get user was called";
		
	}
	
	@PostMapping(value = "/add")
	public  ProjectResponses createUser(@RequestBody ProjectRequest projectRequest) {
		ProjectDto projectDto= new ProjectDto();
		BeanUtils.copyProperties(projectRequest, projectDto);
		 UserDto createUser= userService.createUser(projectDto);
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

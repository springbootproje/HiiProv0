package com.java.app.ws.controller;

import com.java.app.ws.Repository.ProjectRepository;
import com.java.app.ws.Repository.UserRepository;
import com.java.app.ws.request.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import com.java.app.ws.request.User;
import com.java.app.ws.responses.UserResponses;
import com.java.app.ws.services.UserService;

import java.util.List;
import java.util.NoSuchElementException;


@RestController

public class UserController {
	/*@Autowired
	UserService userService;*/


	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;


	public UserController(ProjectRepository projectRepository, UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}




	@RequestMapping("/users")
	@GetMapping("/listUsers")
	public List<User> getAllUsers() {
		return userRepository.findAll(); //list of all uers
	}

	@GetMapping(path = "/project/{id}") //get info of a user /finduser
	public Project getUserById(@PathVariable("id") int id){
		return userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Project with id " + id + " not found"));
	}
	@ExceptionHandler(NoSuchElementException.class)
	public ErrorResponse notFound(NoSuchElementException ex){
		return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage()); //if there no user wit this id
	}

	@GetMapping(path = "/userProject")//list user dans un porjet
	public List<User> getUserByProject(@RequestParam("id_p") int id_p) {
		return userRepository.findById_p(id_p); //user id in a project id_p
		//product in events eventId
	}
	@PostMapping
	public  UserResponses createUser(@RequestBody User user) {

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
	
	



package com.java.app.ws.service;

import com.java.app.ws.dto.CreateUserDto;
import com.java.app.ws.dto.UserDto;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;


	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}


	public CreateUserDto createUser(CreateUserDto createUserDto) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(createUserDto, userEntity);
		// Encrypting the password before saving the user
		userEntity.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
		userEntity.setCreateDate(LocalDate.now());
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		UserEntity result = userRepository.save(userEntity);
		//return UserDto in order to show ID
		return createUserDto;
	}

	public List<UserDto> getAllUsers() {
		List<UserEntity>  userEntityList = userRepository.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for(UserEntity entity : userEntityList){
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(entity, dto);
			userDtoList.add(dto);
		}
		return userDtoList;
	}



	public UserEntity getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}





	public boolean updatePassword(Long userId, String currentPassword, String newPassword) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Verify the current password matches
		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			throw new RuntimeException("Current password is incorrect");
		}


		if (newPassword == null || newPassword.length() < 8) {
			throw new RuntimeException("New password does not meet requirements");
		}


		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

		return true; // Indicate the operation was successful
	}



	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public UserEntity getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found by email"));
	}



	public UserEntity updateUser(Long id, UserEntity userDetails) {
		// Fetch the existing user by id.
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));

		//IF MAIL TAKEN OU PAS
		if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty() && !userDetails.getEmail().equals(user.getEmail())) {
			Optional<UserEntity> existingUserWithEmail = userRepository.findByEmail(userDetails.getEmail());
			if (existingUserWithEmail.isPresent()) {
				throw new RuntimeException("Email is already in use by another user");
			}
			user.setEmail(userDetails.getEmail());
		}

		//UPDATE SANS PASSWORD
		if (userDetails.getFirstName() != null && !userDetails.getFirstName().isEmpty()) {
			user.setFirstName(userDetails.getFirstName());
		}
		if (userDetails.getLastName() != null && !userDetails.getLastName().isEmpty()) {
			user.setLastName(userDetails.getLastName());
		}
		if (userDetails.getTelephone() != null && !userDetails.getTelephone().isEmpty()) {
			user.setTelephone(userDetails.getTelephone());
		}
		if (userDetails.getRole() != null && !userDetails.getRole().isEmpty()) {
			user.setRole(userDetails.getRole());
		}

		return userRepository.save(user);
	}
}


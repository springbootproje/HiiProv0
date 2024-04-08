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
		UserEntity result = userRepository.save(userEntity); //sauvegarde
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



	public UserDto getUserById(Long id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Crée une nouvelle instance de UserDto
		UserDto userDto = new UserDto();

		// Copie les propriétés de userEntity vers userDto
		BeanUtils.copyProperties(userEntity, userDto);

		// Retourne le DTO au lieu de l'entité
		return userDto;
	}






	public boolean updatePassword(Long userId, String currentPassword, String newPassword) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			throw new RuntimeException("Current password is incorrect");
		}

		if (newPassword.length() < 8) {
			throw new RuntimeException("New password must be at least 8 characters long");
		}

		// Ajoute ici d'autres règles si nécessaire

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		return true;
	}




	public void deleteUser(Long id) { //***
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));
		userRepository.delete(user);
	}





	public UserDto updateUser(Long id, UserDto userDetailsDto) { //done pour test de la methode ****
		// Fetch the existing user by id.
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));

		// Check if the email has changed and is not taken by another user.
		if (userDetailsDto.getEmail() != null && !userDetailsDto.getEmail().isEmpty() && !userDetailsDto.getEmail().equals(userEntity.getEmail())) {
			Optional<UserEntity> existingUserWithEmail = userRepository.findByEmail(userDetailsDto.getEmail());
			if (existingUserWithEmail.isPresent()) {
				throw new RuntimeException("Email is already in use by another user");
			}
			userEntity.setEmail(userDetailsDto.getEmail());
		}

		// Only update the fields that are allowed to change
		if (userDetailsDto.getFirstName() != null && !userDetailsDto.getFirstName().isEmpty()) {
			userEntity.setFirstName(userDetailsDto.getFirstName());
		}
		if (userDetailsDto.getLastName() != null && !userDetailsDto.getLastName().isEmpty()) {
			userEntity.setLastName(userDetailsDto.getLastName());
		}

		// Save the updated entity
		UserEntity updatedUserEntity = userRepository.save(userEntity);

		// Convert the updated entity back to a DTO
		UserDto updatedUserDto = new UserDto();
		BeanUtils.copyProperties(updatedUserEntity, updatedUserDto);

		// Return the updated DTO
		return updatedUserDto;
	}


}



package com.java.app.ws.service.Impl;

import com.java.app.ws.Response.LoginResponse;
import com.java.app.ws.dto.LoginDTO;
import com.java.app.ws.dto.UpdateUserDto;
import com.java.app.ws.dto.UserDto;
import com.java.app.ws.repository.LoginRepo;
import com.java.app.ws.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.java.app.ws.repository.UserRepository;
import com.java.app.ws.dto.CreateUserDto;
import com.java.app.ws.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
@Autowired
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private LoginRepo loginRepo;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	
	}

//class mere service
@Override
public com.java.app.ws.dto.UserDto createUser(CreateUserDto createUserDto) {
	// Check if the user already exists
	Optional<UserEntity> existingUser = userRepository.findByEmail(createUserDto.getEmail());
	if (existingUser.isPresent()) {
		throw new IllegalArgumentException("User with email " + createUserDto.getEmail() + " already exists");
	}

	// Create new user entity and copy properties from DTO
	UserEntity userEntity = new UserEntity();
	BeanUtils.copyProperties(createUserDto, userEntity);
	userEntity.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
	userEntity.setCreateDate(LocalDate.now());

	// Save new user entity to the repository
	UserEntity savedUser = userRepository.save(userEntity);

	// Create new UserDto and copy properties from saved user entity
	com.java.app.ws.dto.UserDto newUserDto = new com.java.app.ws.dto.UserDto();
	BeanUtils.copyProperties(savedUser, newUserDto);

	return newUserDto;
}

	@Override
	public void changePassword(String email, String currentPassword, String newPassword) {
		Optional<UserEntity> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			UserEntity user = userOptional.get();

			// Vérifier que le mot de passe actuel est correct
			if (passwordEncoder.matches(currentPassword, user.getPassword())) {
				// Encoder le nouveau mot de passe
				user.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(user); // Sauvegarder les changements
			} else {
				throw new RuntimeException("Current password is incorrect");
			}
		} else {
			throw new RuntimeException("User not found");
		}
	}
	@Override
	public List<UserDto> getAllUsers() {
		List<UserEntity> userEntityList = userRepository.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for(UserEntity entity : userEntityList){
		UserDto dto = new UserDto();
			BeanUtils.copyProperties(entity, dto);
			userDtoList.add(dto);
		}
		return userDtoList;
	}

	@Override
	public UserDto getUserById(Long id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));
		com.java.app.ws.dto.UserDto userDto = new  UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}

	@Transactional
	@Override
	public com.java.app.ws.dto.UserDto updateUser(Long id, UpdateUserDto userDetails) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));

		// Vérifiez si l'email a été modifié et est unique.
		if (userDetails.getEmail() != null && !userDetails.getEmail().equals(userEntity.getEmail())) {
			userRepository.findByEmail(userDetails.getEmail())
					.ifPresent(existingUser -> {
						if (!Objects.equals(existingUser.getId(), userEntity.getId())) {
							throw new RuntimeException("Email is already in use by another user");
						}
					});
			userEntity.setEmail(userDetails.getEmail());
		}

		// Mise à jour des champs s'ils sont présents dans userDetails.
		if (userDetails.getFirstName() != null && !userDetails.getFirstName().isEmpty()) {
			userEntity.setFirstName(userDetails.getFirstName());
		}

		if (userDetails.getLastName() != null && !userDetails.getLastName().isEmpty()) {
			userEntity.setLastName(userDetails.getLastName());
		}

		UserEntity updatedUser = userRepository.save(userEntity);

		// Préparation du DTO de retour.
		UserDto updatedUserDto = new UserDto();
		BeanUtils.copyProperties(updatedUser, updatedUserDto);
		return updatedUserDto;
	}

	@Override
	public void deleteUser(Long id) {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));
		userRepository.delete(user);
	}


}

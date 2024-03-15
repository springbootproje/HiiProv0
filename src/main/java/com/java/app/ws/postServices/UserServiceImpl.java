package com.java.app.ws.postServices;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.app.ws.UserRepositery;
import com.java.app.ws.entity.UserEntity;
import com.java.app.ws.services.UserService;
import com.java.app.ws.shared.Utils;
import com.java.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepositery userRepository;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Autowired
	Utils util;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		
		UserEntity checUser= userRepository.findByEmail(user .getEmail());
		 if (checUser !=null)
		 throw new RuntimeException("User Already Exists!");
		 
		UserEntity userEntity = new UserEntity();
		
		BeanUtils.copyProperties(user,userEntity);
		
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder().encode(user.getPassword()));
		userEntity.setEncryptedPassword(util.generateUserId(32));
		userEntity.setUserId("user test id");
	
		
		UserEntity newUser = userRepository.save(userEntity);
		UserDto userDto =new UserDto();
			BeanUtils.copyProperties(newUser,userDto);
			
		return userDto;
	}

}

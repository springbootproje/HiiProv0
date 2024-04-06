package com.java.app.ws.postServices;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.app.ws.UserRepositery;
import com.java.app.ws.entity.UserEntity;
import com.java.app.ws.Service.UserService;

import com.java.app.ws.shared.dto.UserDto;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepositery userRepository;
	@Autowired
	 BCryptPasswordEncoder bCryptPasswordEncoder;
	

	
	@Override
	public UserDto createUser(UserDto user) {
		
		
		UserEntity checUser= userRepository.findByEmail(user .getEmail());
		 if (checUser !=null)
		 throw new RuntimeException("User Already Exists!");
		 
		UserEntity userEntity = new UserEntity();
		
		BeanUtils.copyProperties(user,userEntity);
		
		

	
		
		UserEntity newUser = userRepository.save(userEntity);
		UserDto userDto =new UserDto();
			BeanUtils.copyProperties(newUser,userDto);
			
		return userDto;
	}

}

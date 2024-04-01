package com.java.app.ws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.app.ws.entity.UserEntity;
@Repository

public  interface UserRepositery extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	

}

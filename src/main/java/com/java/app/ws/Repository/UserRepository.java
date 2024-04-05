package com.java.app.ws.Repository;

import com.java.app.ws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;





import com.java.app.ws.entity.UserEntity;
@Repository

public  interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}

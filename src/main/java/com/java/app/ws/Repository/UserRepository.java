package com.java.app.ws.Repository;

import com.java.app.ws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;





import com.java.app.ws.entity.UserEntity;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

}
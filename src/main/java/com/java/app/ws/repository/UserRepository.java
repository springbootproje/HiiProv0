package com.java.app.ws.repository;

import com.java.app.ws.dto.UserDto;
import com.java.app.ws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

}
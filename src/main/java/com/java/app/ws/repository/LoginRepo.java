package com.java.app.ws.repository;

import com.java.app.ws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface LoginRepo extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findOneByEmailAndPassword(String email, String password);
    UserEntity findByEmail(String email);


}

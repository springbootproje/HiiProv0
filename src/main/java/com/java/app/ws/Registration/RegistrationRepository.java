package com.java.app.ws.Registration;

import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<UserEntity, Long> {

}

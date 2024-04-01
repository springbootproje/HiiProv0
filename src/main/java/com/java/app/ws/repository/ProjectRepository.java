package com.java.app.ws.repository;

import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public  interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {
	ProjectEntity findByEmail(String email);
//	List<ProjectEntity> findAll();
//
//	List<ProjectEntity> findAllByLanguage(Long id);
//
//	List<ProjectEntity> getCustomProjectData(Long id, String description);
}

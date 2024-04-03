package com.java.app.ws.Repository;

import com.java.app.ws.entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {


    ProjectEntity findByEmail(String email);
    List<ProjectEntity> findAll();


}

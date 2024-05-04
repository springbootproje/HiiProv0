package com.java.app.ws.repository;

import com.java.app.ws.entity.ParticipantEntity;
import com.java.app.ws.entity.ParticipantId;
import com.java.app.ws.entity.ProjectEntity;
import com.java.app.ws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepo extends JpaRepository<ParticipantEntity, Long> {
    boolean existsByUserAndProject( UserEntity user, ProjectEntity project);
    ParticipantEntity findByUserAndProject(UserEntity user, ProjectEntity project);


    Optional<ParticipantEntity> findById(ParticipantId participantId);

}
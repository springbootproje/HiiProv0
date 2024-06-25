package com.java.app.ws.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name = "paticipe")
public class ParticipantEntity implements Serializable {

    @EmbeddedId
    private ParticipantId id;  // Composite key

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "projects_id", insertable = false, updatable = false)
    private ProjectEntity project;

    public ParticipantEntity() {
    }

    public ParticipantId getId() {
        return id;
    }

    public void setId(ParticipantId id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
        if (id != null) {
            id.setUserId(user.getId());
        }
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
        if (id != null) {
            id.setProjectId(project.getId());
        }
    }
}
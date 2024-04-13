package com.java.app.ws.dto;

import com.java.app.ws.entity.UserEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectCreationDto {
    private String title;
    private String description;
    private Long creatorUserId; // hadou houma liste des membre normalement

    public Long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(Long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

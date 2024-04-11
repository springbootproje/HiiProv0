package com.java.app.ws.dto;

import java.util.List;

public class ProjectCreationDto {
    private String title;
    private String description;
     private Long userId; // Supposons que cet ID est l'ID de l'utilisateur qui crée le projet

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

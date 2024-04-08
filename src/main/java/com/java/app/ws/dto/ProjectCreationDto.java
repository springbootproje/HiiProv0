package com.java.app.ws.dto;

import java.util.List;

public class ProjectCreationDto {
    private String title;
    private String description;
    private List<Long> memberIds; // Liste des ID des utilisateurs qui seront membres du projet.

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

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}

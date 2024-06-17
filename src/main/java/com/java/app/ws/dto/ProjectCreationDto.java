package com.java.app.ws.dto;

import java.util.List;

public class ProjectCreationDto {
    private String title;
    private String description;
    private List<Long> memberIds; // List of member IDs to be added to the project

    // Getters and setters
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

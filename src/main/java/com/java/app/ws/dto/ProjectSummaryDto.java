package com.java.app.ws.dto;

import java.time.LocalDate;
import java.util.List;

public class ProjectSummaryDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate createDate;
    private List<UserDto> members;
    private List<TacheDto> tasks; // Add this field

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public List<UserDto> getMembers() {
        return members;
    }

    public void setMembers(List<UserDto> members) {
        this.members = members;
    }

    public List<TacheDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TacheDto> tasks) {
        this.tasks = tasks;
    }
}

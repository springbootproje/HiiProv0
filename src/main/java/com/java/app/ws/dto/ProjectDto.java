package com.java.app.ws.dto;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class ProjectDto implements Serializable  {

    @Serial
    private static final long serialVersionUID= 738341912492712033L;


    private String description;
    private String title;
    private Long userId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user) {
        this.userId = user;
    }
}

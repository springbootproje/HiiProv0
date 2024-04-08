package com.java.app.ws.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.java.app.ws.dto.UserDto;
import jakarta.persistence.*;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name="project")
public class ProjectEntity implements Serializable  {

    @Serial
    private static final long serialVersionUID= 738341912492712033L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false, updatable = false)
    private Long id;


    @Column(nullable=false,length=250)
    private String description;


    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;


    @Column(nullable=false,length=50)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserEntity getUser() {return user;}

   public void setUser(UserEntity user) {
        this.user = user;}
}
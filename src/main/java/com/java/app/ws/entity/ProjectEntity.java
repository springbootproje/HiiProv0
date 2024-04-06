package com.java.app.ws.entity;

import jakarta.persistence.*;


import java.io.Serial;
import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDate;

@Entity(name="project")
public class ProjectEntity implements Serializable  {

    @Serial
    private static final long serialVersionUID= 738341912492712033L;
    @jakarta.persistence.Id
    @GeneratedValue
    @Column(nullable=false)
    private int id_p;


    @Column(nullable=false,length=250)
    private String description;


    @Column(name = "create_date")
    private LocalDate createDate;

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }



    @Column(nullable=false,length=50)
    private String project_title;

    @ManyToOne
    @JoinColumn(name = "id") // This should match the foreign key column in your database.
    private UserEntity user;
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    @Id
    private Long idp;

    public void setIdp(Long idp) {
        this.idp = idp;
    }

    public Long getIdp() {
        return idp;
    }
}

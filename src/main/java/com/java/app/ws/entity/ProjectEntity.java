package com.java.app.ws.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


import java.io.Serial;
import java.io.Serializable;
import java.security.Timestamp;

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


    @Column(nullable=false,length=50)
    private Timestamp create_date;

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

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable=false,length=50)
    private String project_title;

    @Column(nullable=false)
    private int id;
    @Id
    private Long idp;

    public void setIdp(Long idp) {
        this.idp = idp;
    }

    public Long getIdp() {
        return idp;
    }
}

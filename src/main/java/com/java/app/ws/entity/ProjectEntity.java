package com.java.app.ws.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.io.Serializable;
import java.security.Timestamp;

@Entity(name="project")
public class ProjectEntity implements Serializable  {



    @Column(nullable=false)
    private int id_p;


    @Column(nullable=false,length=250)
    private String description;


    @Column(nullable=false,length=50)
    private Timestamp create_date;


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

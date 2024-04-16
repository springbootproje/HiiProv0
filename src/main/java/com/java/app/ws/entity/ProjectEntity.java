package com.java.app.ws.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "assignedProjects")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<UserEntity> members= new HashSet<>() ;

    public Set<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<UserEntity> members) {
        this.members = members;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity creator;



    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }


    // Relation avec les tâches
    @OneToMany(mappedBy = "project")
    private List<TacheEntity> taches = new ArrayList<>();


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


}
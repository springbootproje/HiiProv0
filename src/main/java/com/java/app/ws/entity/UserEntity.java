package com.java.app.ws.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;


@Entity(name="user") //lier a table user
public class UserEntity implements Serializable{


    private static final long serialVersionUID = 2565811476026900613L;
    @jakarta.persistence.Id
    @GeneratedValue
    @Column(nullable=false)
    private long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})//ici jai ajouter many to many avec avec had lobjet pareil f projectEntity
    @JoinTable(name = "participe",
            joinColumns = @JoinColumn(name = "projects_id"),
            inverseJoinColumns = @JoinColumn(name ="user_id" )
    )
    //hashset: array avce donee qui ce ne repete pas
    private Set<ProjectEntity> assignedProjects= new HashSet<>() ;







    @Column(nullable=false,length=50)
    private String firstName;


    @Column(nullable=false,length=50)
    private String lastName;


    @Column(nullable=false,length=100,unique=true)
    private String email;

    @Column(nullable=false)
    private String role;



    @Column(nullable=false)
    private String password;
    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;


    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }


    @OneToMany
    private List<ProjectEntity> project;

    public List<ProjectEntity> getProject() {
        return project;
    }

    public void setProject(List<ProjectEntity> project) {
        this.project = project;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public long getId() {
        return id;
    }
}

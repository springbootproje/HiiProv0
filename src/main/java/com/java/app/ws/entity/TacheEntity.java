package com.java.app.ws.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tache")
public class TacheEntity implements Serializable {

    private static final long serialVersionUID = -3002853777465044420L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_t")
    private Long id;

    @Column(name = "title", length = 100, nullable = false) // Add title column
    private String title;
    @Column(name = "dateCreation",nullable = false) // Correction de l'annotation pour dateCreation
    private LocalDate dateCreation;

    @Column(name = "description", length = 250) // Increase length for better descriptions
    private String description;

    @Column(name = "statut", length = 50)
    private String statut;

    @ManyToOne
    @JoinColumn(name = "id_u")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "id_p")
    private ProjectEntity project;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() { // Getter for title
        return title;
    }

    public void setTitle(String title) { // Setter for title
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
}

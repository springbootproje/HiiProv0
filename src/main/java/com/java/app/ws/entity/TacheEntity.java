package com.java.app.ws.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tache")
public class TacheEntity implements Serializable{

 private static final long serialVersionUID= -3002853777465044420L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_t")
    private Long id;

    @Column(name = "description",length = 50)
    private String description;

    @Column(name = "statut", length = 50)
    private String statut;



    @ManyToOne
    @JoinColumn(name = "id_u")
    private UserEntity user;//dans la classe TacheEntity permet de représenter la relation entre une tâche et l'utilisateur qui est responsable de cette tâche.

    @ManyToOne
    @JoinColumn(name = "id_p")
    private ProjectEntity project;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}

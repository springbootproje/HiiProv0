 package com.java.app.ws.entity;

 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.GeneratedValue;
 import jakarta.persistence.NamedQuery;

 import java.io.Serializable;


 @Entity(name="project")
 @NamedQuery(name = "Project.findByCustomLanguage",
         query = "select u from project u where u.language = ?1")
 public class ProjectEntity implements Serializable{

     /**
      *
      */
     private static final long serialVersionUID = 2565811476026900613L;
    @jakarta.persistence.Id
    @GeneratedValue
     private long Id;
     @Column(nullable=false)
     private String description;

     @Column(nullable=false)
     private String language;


     public long getId() {
         return Id;
     }

     public void setId(long id) {
         Id = id;
     }

     public String getDescription() {
         return description;
     }

     public void setDescription(String description) {
         this.description = description;
     }

     public String getLanguage() {
         return language;
     }

     public void setLanguage(String language) {
         this.language = language;
     }
 }
 
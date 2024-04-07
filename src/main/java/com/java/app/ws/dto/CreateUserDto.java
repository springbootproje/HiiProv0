 package com.java.app.ws.dto;

 import java.io.Serializable;

 public class CreateUserDto implements Serializable{

     /**
      *
      */
     private static final long serialVersionUID = 2565811476026900613L;


     private String firstName;

     private String lastName;

     private String email;

     private String role;

     private String telephone;

     private String password;


     public String getFirstName() {
         return firstName;
     }

     public void setFirstName(String firstName) {
         this.firstName = firstName;
     }

     public String getRole() {
         return role;
     }

     public void setRole(String role) {
         this.role = role;
     }


     public String getTelephone() {
         return telephone;
     }

     public void setTelephone(String telephone) {
         this.telephone = telephone;
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

     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }
 }
 
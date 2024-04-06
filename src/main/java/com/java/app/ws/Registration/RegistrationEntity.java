package com.java.app.ws.Registration;

import jakarta.persistence.Column;

public class RegistrationEntity {


    @Column(nullable=false)
    private int id;


    @Column(nullable=false,length=50)
    private String firstName;


    @Column(nullable=false,length=50)
    private String lastName;


    @Column(nullable=false,length=100,unique=true)
    private String email;

    @Column(nullable=false, unique=true)
    private String telephone;

    @Column(nullable=false)
    private String password;
    @Column(nullable=false)
    private String role;



    public String getFisrtName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }












}


package com.java.app.ws.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import java.io.Serializable;
public class UserDto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 2565811476026900613L;

	private Long id;

	private String firstName;

	private String lastName;

	private String email;




	public void setId(Long id) {this.id = id;}
	public long getId() {return id;}


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
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





}

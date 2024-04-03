 package com.java.app.ws.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;



@Entity(name="user") //lier a table user
public class UserEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2565811476026900613L;
   @jakarta.persistence.Id
   @GeneratedValue
	
	private long Id;
    @Column(nullable=false)
	private int id;


	@Column(nullable=false,length=50)
	private String firstName;


	@Column(nullable=false,length=50)
	private String lastName;


	@Column(nullable=false,length=100,unique=true)
	private String email;


	//@Column(nullable=false)
	//private String encryptedPassword;


	//@Column(nullable=true)
	//private String emailverificationToken;


	//@Column( nullable=false)
	//private Boolean emailverificationStatus=false;

	@Column(nullable=false)
	private String telephone;

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

	public void setId(int id) {
		this.id = id;
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

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	@Column(nullable=false)
	private String role;


	/*public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFisrtName(String firstName) {
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
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public String getEmailverificationToken() {
		return emailverificationToken;
	}
	public void setEmailverificationToken(String emailverificationToken) {
		this.emailverificationToken = emailverificationToken;
	}
	public Boolean getEmailverificationStatus() {
		return emailverificationStatus;
	}
	public void setEmailverificationStatus(Boolean emailverificationStatus) {
		this.emailverificationStatus = emailverificationStatus;
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
	
*/
}
 
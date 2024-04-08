 package com.java.app.ws.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;


 @Entity(name="user") //lier a table user
public class UserEntity implements Serializable{


	private static final long serialVersionUID = 2565811476026900613L;
    @jakarta.persistence.Id
    @GeneratedValue
    @Column(nullable=false)
	private long id;


	@Column(nullable=false,length=50)
	private String firstName;


	@Column(nullable=false,length=50)
	private String lastName;


	@Column(nullable=false,length=100,unique=true)
	private String email;

	@Column(nullable=false)
	private String role;

	@Column(nullable=false, unique=true)
	private String telephone;

	@Column(nullable=false)
	private String password;

	 public LocalDate getCreateDate() {
		 return createDate;
	 }

	 public void setCreateDate(LocalDate createDate) {
		 this.createDate = createDate;
	 }

	 @Column(name = "create_date", nullable = false)
	 private LocalDate createDate;

	@OneToMany
	private List<ProjectEntity> projects;

	 public List<ProjectEntity> getProjects() {
		 return projects;
	 }

	 public void setProjects(List<ProjectEntity> projects) {
		 this.projects = projects;
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
		return id;
	}
}
 
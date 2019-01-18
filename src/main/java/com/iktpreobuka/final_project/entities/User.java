package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="user")
public class User {


	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String email;
	@Column
	private String password;
	@Column(unique = true)
	private String username;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "role")
	private Role role;
	
	@OneToMany(mappedBy = "user_id", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Pupil> pupils = new ArrayList<>();
	
	@OneToMany(mappedBy = "user_id", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Parent> parents = new ArrayList<>();
	
	@OneToMany(mappedBy = "user_id", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Professor> professors = new ArrayList<>();
	
	
	
	public Long getId() {
		return id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public User() {
		super();
	}
	public User(String email, String password, String username, Role role) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
	}
	public User(String email, String password, String username) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
	}
	
	
	
	
}

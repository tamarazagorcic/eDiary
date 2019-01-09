package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table (name="parent")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Parent {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String code;
	
	@Version
	@ColumnDefault("0")
	private Integer version;
	
	@JsonIgnore
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Pupil> parent_pupils = new ArrayList<>();

	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<Pupil> getParent_pupils() {
		return parent_pupils;
	}

	public void setParent_pupils(List<Pupil> parent_pupils) {
		this.parent_pupils = parent_pupils;
	}

	public Parent() {
		super();
	}

	public Parent(String name, String surname, String email, String code) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.code = code;
	}
	
	
	
}

package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name="professor",uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Professor {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@Column
	private String surname;
	@Column(unique = true)
	private String code;
	@Version
	@ColumnDefault("0")
	private Integer version;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "professor", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<ProfessorSubject> subjects = new ArrayList<>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	public List<ProfessorSubject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<ProfessorSubject> subjects) {
		this.subjects = subjects;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Professor() {
		super();
	}
	public Professor(String name, String surname, String code) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
	}
	
	
	
}

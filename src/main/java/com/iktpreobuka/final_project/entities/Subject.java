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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name="subject")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Subject {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String code;
	private Integer version;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<ProfessorSubject> professors = new ArrayList<>();
	
	
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
	public Subject() {
		super();
	}
	
	
	
}

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
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name="pupil")

public class Pupil {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@Column
	private String surname;
	@Column
	private String jmbg;
	@Column(unique=true)
	private String code;
	@Version
	@ColumnDefault("0")
	private Integer version;
	
	@JsonIgnore
	@OneToMany(mappedBy = "pupil", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<PupilsInClass> schoolClasses = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private Parent parent;
	
	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
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
	public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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
	public Pupil() {
		super();
	}
	public Pupil(String name, String surname, String jmbg, String code, Parent parent) {
		super();
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.parent = parent;
	}

	
	
}

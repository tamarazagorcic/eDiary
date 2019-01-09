package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name="professorSubject")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ProfessorSubject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	private Integer version;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "professor")
	private Professor professor;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subject")
	private Subject subject;
	
	@JsonIgnore
	@OneToMany(mappedBy = "professorSubject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<ProfessorSubjectClass> profesor_subject_class = new ArrayList<>();
	
	
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public ProfessorSubject() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
}

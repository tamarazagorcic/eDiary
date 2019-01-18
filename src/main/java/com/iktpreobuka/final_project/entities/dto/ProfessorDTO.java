package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.util.View;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class ProfessorDTO {

	@JsonIgnore
	private Long id;
	
	@JsonView(View.Public.class)
	@NotNull(message = "Name must be provided.")
	@Size(min=2, max=30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonView(View.Public.class)
	@NotNull(message = "Surname must be provided.")
	@Size(min=2, max=30, message = "Surname must be between {min} and {max} characters long.")
	private String surname;
	
	@JsonView(View.Admin.class)
	@NotNull(message = "Code must be provided.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
//	@JsonIgnore
//	@JsonBackReference("profesorsubjects")
//	private List<ProfessorSubject> subjects;
	
	
	@JsonView(View.Public.class)
	private List<SubjectDTO> subjects;
	
	@JsonView(View.Admin.class)
	private UserDTO professorUser;
	
	

	public UserDTO getProfessorUser() {
		return professorUser;
	}

	public void setProfessorUser(UserDTO professorUser) {
		this.professorUser = professorUser;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public List<ProfessorSubject> getSubjects() {
//		return subjects;
//	}
//
//	public void setSubjects(List<ProfessorSubject> subjects) {
//		this.subjects = subjects;
//	}

	
	public ProfessorDTO() {
		super();
	}

	public List<SubjectDTO> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectDTO> subjects) {
		this.subjects = subjects;
	}

	public ProfessorDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
	}

	public ProfessorDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			UserDTO professorUser) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.professorUser = professorUser;
	}

	public ProfessorDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			List<SubjectDTO> subjects) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.subjects = subjects;
	}
	
	
	
	
}

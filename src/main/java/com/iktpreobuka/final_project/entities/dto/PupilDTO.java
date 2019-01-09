package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.util.View;
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PupilDTO {

	
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
	
	@JsonView(View.Private.class)	
	@NotNull(message = "JMBG must be provided.")
	@Pattern(regexp="^[0-9]{13}$")
	private String jmbg;
	
	@JsonView(View.Admin.class)
	@NotNull(message = "Code must be provided.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
	
	@JsonIgnore
	@JsonBackReference("pupilsclasses")
	private List<PupilsInClass> schoolClasses;
	
	
	@JsonView(View.Private.class)
	@JsonManagedReference("parentpupils")
	private Parent parent;

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

	public List<PupilsInClass> getSchoolClasses() {
		return schoolClasses;
	}

	public void setSchoolClasses(List<PupilsInClass> schoolClasses) {
		this.schoolClasses = schoolClasses;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public PupilDTO() {
		super();
	}

	public PupilDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "JMBG must be provided.") @Pattern(regexp = "^[0-9]{13}$") String jmbg,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			Parent parent) {
		super();
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.parent = parent;
	}

	
	
	
	
	
}
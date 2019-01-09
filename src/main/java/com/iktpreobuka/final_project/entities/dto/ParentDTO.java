package com.iktpreobuka.final_project.entities.dto;



import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.util.View;
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ParentDTO {

	
	@JsonIgnore
	private Long id;
	@JsonView(View.Public.class)
	@NotNull(message = "Name must be provided.")
	@Size(min=2, max=30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	@JsonView(View.Public.class)
	@NotNull(message = "Surname must be provided.")
	@Size(min=2, max=30, message = "Surame must be between {min} and {max} characters long.")
	private String surname;
	@JsonView(View.Admin.class)
	@NotNull(message = "Email must be provided.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", 
	message="Email is not valid.")
	private String email;
	@JsonView(View.Admin.class)
	@NotNull(message = "Code must be provided.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
	
	
	@JsonIgnore
	@JsonBackReference("parentpupils")	
	private List<Pupil> parent_pupils;


	
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


	public List<Pupil> getParent_pupils() {
		return parent_pupils;
	}


	public void setParent_pupils(List<Pupil> parent_pupils) {
		this.parent_pupils = parent_pupils;
	}





	public ParentDTO(String name, String surname, String email, String code) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.code = code;
	}


	public ParentDTO() {
		super();
	}
	
	
	
	
	
}

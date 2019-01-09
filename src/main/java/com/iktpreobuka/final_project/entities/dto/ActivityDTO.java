package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.util.View;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ActivityDTO {

	@JsonIgnore
	private Long id;
	
	@JsonView(View.Public.class)
	@NotNull(message = "Name must be provided.")
	@Size(min=2, max=30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonView(View.Admin.class)
	@NotNull(message = "Code must be provided.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
	
	@JsonIgnore
	@JsonBackReference("activitymark")
	private List<Mark> marks;

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


	public List<Mark> getMarks() {
		return marks;
	}

	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}

	public ActivityDTO(String name, String code) {
		super();
		
		this.name = name;
		this.code = code;
		
	}

	public ActivityDTO() {
		super();
	}
	
	
	
}

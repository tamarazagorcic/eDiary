package com.iktpreobuka.final_project.entities.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

public class RoleDTO {

	@JsonIgnore
	private Long id;
	
	@JsonView(View.Admin.class)
	@NotNull(message = "Name must be provided.")
	@Size(min=2, max=30, message = "Name must be between {min} and {max} characters long.")
	private	String name;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name) {
		super();
		this.name = name;
	}

	public RoleDTO() {
		super();
	}
	
	
}

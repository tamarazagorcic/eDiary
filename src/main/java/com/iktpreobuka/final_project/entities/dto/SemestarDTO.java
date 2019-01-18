package com.iktpreobuka.final_project.entities.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

@JsonView(View.Public.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SemestarDTO {
	
	
	@JsonIgnore
	private Long id;
	
	@JsonView(View.Public.class)
	@NotNull(message = "Name must be provided.")
	@Size(min=2, max=10, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonView(View.Public.class)
	@NotNull(message = "Value must be provided.")
	@Range(min=1, max=2)
	private Integer value;
	
	@JsonView(View.Public.class)
	@NotNull(message = "Start date must be provided.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Europe/Belgrade")
	private LocalDate startDate;
	
	@JsonView(View.Public.class)
	@NotNull(message = "End date must be provided.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Europe/Belgrade")
	private LocalDate endDate;
	
	@JsonView(View.Admin.class)
	@NotNull(message = "Code must be provided.")
	@Size(min=2, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
	@JsonIgnore
	@JsonBackReference("classsemestar")
	private List<SchoolClassDTO> classes;


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


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}





	public List<SchoolClassDTO> getClasses() {
		return classes;
	}


	public void setClasses(List<SchoolClassDTO> classes) {
		this.classes = classes;
	}


	public SemestarDTO() {
		super();
	}





	public SemestarDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 10, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Value must be provided.") @Range(min = 1, max = 2) Integer value,
			@NotNull(message = "Code must be provided.") @Size(min = 2, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.name = name;
		this.value = value;
		this.code = code;
	}



	public SemestarDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 10, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Value must be provided.") @Range(min = 1, max = 2) Integer value,
			@NotNull(message = "Start date must be provided.") LocalDate startDate,
			@NotNull(message = "End date must be provided.") LocalDate endDate,
			@NotNull(message = "Code must be provided.") @Size(min = 2, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.name = name;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
		this.code = code;
	}
	
	
}

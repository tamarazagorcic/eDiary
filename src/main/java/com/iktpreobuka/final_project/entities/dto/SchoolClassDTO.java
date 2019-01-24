package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.enumerations.EGrade;
import com.iktpreobuka.final_project.util.View;
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SchoolClassDTO {

	@JsonIgnore
	private Long id;
	
	@JsonView(View.Admin.class)
	@NotNull(message = "Code must be provided.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
	
	@JsonView(View.Public.class)
	@NotNull(message = "Grade must be provided.")
	private EGrade grade;
	
	@JsonView(View.Public.class)
	private SemestarDTO semestarDTO;
	
	@JsonView(View.Public.class)
	private List<PupilDTO> pupilsAttendingClass;
	
	
//	@JsonView(View.Public.class)
//	//@JsonManagedReference("classsemestar")
//	private Semestar semestar;
	
	@JsonIgnore
	@JsonBackReference("classpupils")
	private List<PupilsInClass> pupils;
	
	@JsonIgnore
	@JsonBackReference("classprofesorsubject")
	private List<ProfessorSubjectClass> professors_subjects;

	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public EGrade getGrade() {
		return grade;
	}

	public void setGrade(EGrade grade) {
		this.grade = grade;
	}



	public List<PupilsInClass> getPupils() {
		return pupils;
	}

	public void setPupils(List<PupilsInClass> pupils) {
		this.pupils = pupils;
	}

	public List<ProfessorSubjectClass> getProfessors_subjects() {
		return professors_subjects;
	}

	public void setProfessors_subjects(List<ProfessorSubjectClass> professors_subjects) {
		this.professors_subjects = professors_subjects;
	}

	
	public SemestarDTO getSemestarDTO() {
		return semestarDTO;
	}

	public void setSemestarDTO(SemestarDTO semestarDTO) {
		this.semestarDTO = semestarDTO;
	}

	
	public List<PupilDTO> getPupilsAttendingClass() {
		return pupilsAttendingClass;
	}

	public void setPupilsAttendingClass(List<PupilDTO> pupilsAttendingClass) {
		this.pupilsAttendingClass = pupilsAttendingClass;
	}

	public SchoolClassDTO() {
		super();
	}

	public SchoolClassDTO(
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade, SemestarDTO semestarDTO) {
		super();
		this.code = code;
		this.grade = grade;
		this.semestarDTO = semestarDTO;
	}

	public SchoolClassDTO(
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade, SemestarDTO semestarDTO,
			List<PupilDTO> pupilsAttendingClass) {
		super();
		this.code = code;
		this.grade = grade;
		this.semestarDTO = semestarDTO;
		this.pupilsAttendingClass = pupilsAttendingClass;
	}

	public SchoolClassDTO(
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade) {
		super();
		this.code = code;
		this.grade = grade;
	}

	




	
	
	
}

package com.iktpreobuka.final_project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.Activity;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.entities.dto.ActivityDTO;
import com.iktpreobuka.final_project.entities.dto.SchoolClassDTO;
import com.iktpreobuka.final_project.entities.dto.SemestarDTO;
import com.iktpreobuka.final_project.entities.dto.SubjectDTO;
import com.iktpreobuka.final_project.services.SubjectService;
import com.iktpreobuka.final_project.util.SubjectCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	SubjectCustomValidator subjectValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(subjectValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	// @Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllSubjectsAdmin() {
		try {
			List<SubjectDTO> list = new ArrayList<>();
			for (Subject subject : subjectService.getAllSubject()) {
				SubjectDTO subjectDTO = new SubjectDTO(subject.getId(), subject.getName(), subject.getCode());
				list.add(subjectDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<SubjectDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Subject"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// @Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findBySubjectId(@PathVariable Long id) {

		try {
			Optional<Subject> subject = subjectService.findById(id);
			if (subject.isPresent()) {
				SubjectDTO subjectDTO = new SubjectDTO(subject.get().getId(), subject.get().getName(),
						subject.get().getCode());
				return new ResponseEntity<SubjectDTO>(subjectDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewSubject(@Valid @RequestBody SubjectDTO newSubject, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			subjectValidator.validate(newSubject, result);
		}
		if (subjectService.ifExists(newSubject.getCode())) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Code for subject is present"),
					HttpStatus.BAD_REQUEST);
		}
		if (subjectService.ifExistsName(newSubject.getName())) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Name for subject is present"),
					HttpStatus.BAD_REQUEST);
		}

		Subject newSubjectEntity = new Subject(newSubject.getName(), newSubject.getCode());

		subjectService.addNewSubject(newSubjectEntity);
		SubjectDTO subjectDTO = new SubjectDTO(newSubjectEntity.getId(), newSubjectEntity.getName(),
				newSubjectEntity.getCode());
		return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
	}

	// @Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateActivity(@Valid @RequestBody SubjectDTO newSubject, @PathVariable Long id,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} else {
				subjectValidator.validate(newSubject, result);
			}

			Optional<Subject> subject = subjectService.findById(id);
			if (subject.isPresent()) {
				if (!subject.get().getCode().equals(newSubject.getCode())) {
					if (subjectService.ifExists(newSubject.getCode())) {
						return new ResponseEntity<RESTError>(new RESTError(1, "Code for subject is present"),
								HttpStatus.BAD_REQUEST);
					} else {
						subject.get().setCode(newSubject.getCode());
					}
				}
				if (!subject.get().getName().equals(newSubject.getName())) {
					if (subjectService.ifExistsName(newSubject.getName())) {
						return new ResponseEntity<RESTError>(new RESTError(1, "Name for subject is present"),
								HttpStatus.BAD_REQUEST);
					} else {
						subject.get().setName(newSubject.getName());
					}
				}

				subjectService.updateSubject(id, subject.get());
				SubjectDTO subjectDTO = new SubjectDTO(subject.get().getId(), subject.get().getName(),
						subject.get().getCode());

				return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteBySubjectId(@PathVariable Long id) {

		try {
			Optional<Subject> subject = subjectService.findById(id);
			if (subject.isPresent()) {

				List<ProfessorSubject> professors = subjectService.findPSBySubject(subject.get());
				if (professors.size() != 0) {
					return new ResponseEntity<RESTError>(
							new RESTError(1,
									"You can not delete subject when there are professors" + " conected to him/her."),
							HttpStatus.BAD_REQUEST);

				} else {
					SubjectDTO subjectDTO = new SubjectDTO(subject.get().getName(), subject.get().getCode());
					subjectService.deleteSubject(id);
					return new ResponseEntity<SubjectDTO>(subjectDTO, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

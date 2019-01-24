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
import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.ProfessorDTO;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.entities.dto.SubjectDTO;
import com.iktpreobuka.final_project.services.ProfessorService;
import com.iktpreobuka.final_project.services.SubjectService;
import com.iktpreobuka.final_project.services.UserService;
import com.iktpreobuka.final_project.util.ProfessorCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/professor")
public class ProfessorController {


	@Autowired
	private ProfessorService professorService;

	@Autowired
	ProfessorCustomValidator professorValidator;
	
	@Autowired
	private UserService userService;
	

	@Autowired
	private SubjectService subjectService;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(professorValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@Secured("admin")
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	public ResponseEntity<?> getAllProfessorsPublic() {
		try {
			List<ProfessorDTO> list = new ArrayList<>();
			for (Professor professor : professorService.getAll()) {
				
				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getName(),temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}
				
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(),professor.getSurname(),professor.getCode(),listSubjectDTO);
				list.add(professorDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<ProfessorDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Professors"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured("admin")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllProfessorsPrivate() {
		try {
			List<ProfessorDTO> list = new ArrayList<>();
			for (Professor professor : professorService.getAll()) {
				
				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getName(),temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(),professor.getSurname(),professor.getCode(),listSubjectDTO);
				list.add(professorDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<ProfessorDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Professors"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllProfessorsAdmin() {
		try {
			List<ProfessorDTO> list = new ArrayList<>();
			for (Professor professor : professorService.getAll()) {
				
				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getName(),temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(),professor.getSurname(),professor.getCode(),listSubjectDTO);
				list.add(professorDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<ProfessorDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Professors"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByProfessorId(@PathVariable Long id) {

		try {
			Optional<Professor> professor = professorService.findById(id);
			if (professor.isPresent()) {
				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.get().getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getName(),temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}
				ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getName(),professor.get().getSurname(),
						professor.get().getCode(),listSubjectDTO);
				return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Professor not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewProfessor(@Valid @RequestBody ProfessorDTO newProfessor, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			professorValidator.validate(newProfessor, result);
		}

		User professorUser = new User(newProfessor.getProfessorUser().getEmail(),newProfessor.getProfessorUser().getPassword(),
				newProfessor.getProfessorUser().getUsername());
		
		User thisUser = userService.addNewUser(professorUser, "ROLE_PROFESSOR");
		
		Professor newProfessorEntity = new Professor(newProfessor.getName(),newProfessor.getSurname(),newProfessor.getCode(),thisUser);

		professorService.addNew(newProfessorEntity);
		
		RoleDTO roleDTO = new RoleDTO(thisUser.getRole().getName());
		
		newProfessor.getProfessorUser().setRole(roleDTO);

		return new ResponseEntity<>(newProfessor, HttpStatus.OK);
	}
	
	
	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateProfessor(@Valid @RequestBody ProfessorDTO newProfessor,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} else {
					professorValidator.validate(newProfessor, result);
				}

			Optional<Professor> professor = professorService.findById(id);
			if (professor.isPresent()) {
				professor.get().setCode(newProfessor.getCode());
				professor.get().setName(newProfessor.getName());
				professor.get().setSurname(newProfessor.getSurname());
				
				//List<Subject> list = newProfessor.getSubjects();

				professorService.update(id, professor.get());

				return new ResponseEntity<>(newProfessor, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Professor not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByProfessorId(@PathVariable Long id) {

		try {
			Optional<Professor> professor = professorService.findById(id);
			if (professor.isPresent()) {

				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.get().getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getName(),temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}
				ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getName(),professor.get().getSurname(),
						professor.get().getCode(),listSubjectDTO);
				professorService.delete(id);
				return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Professor not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{idP}/subject/{idS}")
	public ResponseEntity<?> conectionProfessorSubject(@PathVariable Long idP, @PathVariable Long idS) {

		try {
			Optional<Professor> professor = professorService.findById(idP);
			Optional<Subject> subject = subjectService.findById(idS);
			if (professor.isPresent() && subject.isPresent() && !professorService.ifExistsConectonProfessorSubject(professor.get(), subject.get())) {
				

				professorService.addNewPS(professor.get(), subject.get());
				//professorService.update(id, professor.get());
				
				
				
				List<SubjectDTO> list = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.get().getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getName(),temp.getCode());
					list.add(subjectDTO);
				}
				

				ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getName(),professor.get().getSurname(),professor.get().getCode(),list);
				return new ResponseEntity<>(professorDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Professor not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

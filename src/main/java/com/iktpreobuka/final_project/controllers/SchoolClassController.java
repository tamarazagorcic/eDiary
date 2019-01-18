package com.iktpreobuka.final_project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.entities.dto.ParentDTO;
import com.iktpreobuka.final_project.entities.dto.PupilDTO;
import com.iktpreobuka.final_project.entities.dto.SchoolClassDTO;
import com.iktpreobuka.final_project.entities.dto.SemestarDTO;
import com.iktpreobuka.final_project.services.PupilService;
import com.iktpreobuka.final_project.services.SchoolClassService;
import com.iktpreobuka.final_project.services.SemestarService;
import com.iktpreobuka.final_project.util.SchoolClassCustomValidator;
import com.iktpreobuka.final_project.util.SemestarCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/schoolclass")
public class SchoolClassController {

	@Autowired
	private SchoolClassService scService;

	@Autowired
	SchoolClassCustomValidator scValidator;


	@Autowired
	SemestarCustomValidator semestarValidator;
	
	@Autowired
	private PupilService pService;

	@Autowired
	private SemestarService smService;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(scValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllSCPrivate() {
		try {
			List<SchoolClassDTO> list = new ArrayList<>();
			for (SchoolClass sc : scService.getAll()) {
				Semestar sm = sc.getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getName(), sm.getValue(), sm.getCode());

				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), smDTO);
				list.add(schoolClassDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<SchoolClassDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all school classes"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllSCAdmin() {
		try {
			List<SchoolClassDTO> list = new ArrayList<>();
			for (SchoolClass sc : scService.getAll()) {
				Semestar sm = sc.getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getName(), sm.getValue(), sm.getStartDate(), sm.getEndDate(),
						sm.getCode());

				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), smDTO);
				list.add(schoolClassDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<SchoolClassDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all school classes"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findBySCId(@PathVariable Long id) {

		try {
			Optional<SchoolClass> sc = scService.findById(id);
			if (sc.isPresent()) {
				Semestar sm = sc.get().getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getName(), sm.getValue(),  sm.getStartDate(), sm.getEndDate(),sm.getCode());

				SchoolClassDTO scDTO = new SchoolClassDTO(sc.get().getCode(), sc.get().getGrade(), smDTO);
				return new ResponseEntity<SchoolClassDTO>(scDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "School class not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST) //, consumes = "application/json"
	public ResponseEntity<?> addNewSC(@Valid @RequestBody SchoolClassDTO newSchoolClass, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			scValidator.validate(newSchoolClass, result);
			//semestarValidator.validate(newSchoolClass.getSemestarDTO(), result);
		}

		SemestarDTO smDTO = newSchoolClass.getSemestarDTO();

		if (smService.ifExists(smDTO.getCode())) {

			Semestar semestar = smService.findByCode(newSchoolClass.getSemestarDTO().getCode());
			
				SchoolClass newSCE = new SchoolClass(newSchoolClass.getCode(), newSchoolClass.getGrade(),
						semestar);

				scService.addNew(newSCE);
		
		}else {

			Semestar sm = new Semestar(smDTO.getName(), smDTO.getValue(),  smDTO.getStartDate(), smDTO.getEndDate(),smDTO.getCode());
			smService.addNew(sm);
			
			SchoolClass newSCEntity = new SchoolClass(newSchoolClass.getCode(), newSchoolClass.getGrade(), sm);

			scService.addNew(newSCEntity);
			
		}
		return new ResponseEntity<>(newSchoolClass, HttpStatus.OK);

	}

	
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateSC(@Valid @RequestBody SchoolClassDTO newSchoolClass, @PathVariable Long id,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} else {
				scValidator.validate(newSchoolClass, result);
			}

			Optional<SchoolClass> sc = scService.findById(id);
			if (sc.isPresent()) {
				sc.get().setCode(newSchoolClass.getCode());
				sc.get().setGrade(newSchoolClass.getGrade());
				SemestarDTO smDTO = newSchoolClass.getSemestarDTO();
				
				if (smService.ifExists(smDTO.getCode())) {
					Semestar semestar = smService.findByCode(newSchoolClass.getSemestarDTO().getCode());
					sc.get().setSemestar(semestar);
				}else {
					Semestar sm = new Semestar(smDTO.getName(), smDTO.getValue(),  smDTO.getStartDate(), smDTO.getEndDate(),smDTO.getCode());
					smService.addNew(sm);
					
					sc.get().setSemestar(sm);
					
				}
			
				scService.update(id, sc.get());

				return new ResponseEntity<>(newSchoolClass, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "School class not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteBySCId(@PathVariable Long id) {

		try {
			Optional<SchoolClass> sc = scService.findById(id);
			if (sc.isPresent()) {

				Semestar sm = sc.get().getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getName(), sm.getValue(),  sm.getStartDate(), sm.getEndDate(),sm.getCode());
				
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.get().getCode(), sc.get().getGrade(),smDTO);
				scService.delete(id);
				return new ResponseEntity<SchoolClassDTO>(scDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "School class not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/pupil/{id}")
	public ResponseEntity<?> getAllSchoolClasesByPupil(@PathVariable Long id) {
		try {
			Optional<Pupil> p = pService.findById(id);
			if (p.isPresent()) {

				List<SchoolClassDTO> list = new ArrayList<>();
				for (SchoolClass sc : scService.findClassesByPupils(id)) {
					Semestar sm = sc.getSemestar();
					SemestarDTO smDTO = new SemestarDTO(sm.getName(), sm.getValue(),  sm.getStartDate(), sm.getEndDate(),sm.getCode());
					SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), smDTO);
					list.add(schoolClassDTO);
				}

				if (list.size() != 0) {

					return new ResponseEntity<Iterable<SchoolClassDTO>>(list, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all school classes"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/pupilandclass/{id}")
	public ResponseEntity<?> getAllSchoolClasesByPupilId(@PathVariable Long id) {
		try {
			Optional<Pupil> p = pService.findById(id);
			Parent pt = p.get().getParent();
			ParentDTO ptDTO = new ParentDTO(pt.getName(), pt.getSurname(), pt.getCode());
			if (p.isPresent()) {

				List<SchoolClassDTO> list = new ArrayList<>();
				for (SchoolClass sc : scService.findClassesByPupils(id)) {

					Semestar sm = sc.getSemestar();
					SemestarDTO smDTO = new SemestarDTO(sm.getName(), sm.getValue(), sm.getCode());

					SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), smDTO);

					list.add(schoolClassDTO);
				}

				PupilDTO pupilDTO = new PupilDTO(p.get().getName(), p.get().getSurname(), p.get().getJmbg(),
						p.get().getCode(), ptDTO, list);

				if (list.size() != 0) {

					return new ResponseEntity<PupilDTO>(pupilDTO, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all school classes"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

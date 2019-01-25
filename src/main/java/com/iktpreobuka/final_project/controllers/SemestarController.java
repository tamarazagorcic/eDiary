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
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.entities.dto.SemestarDTO;
import com.iktpreobuka.final_project.services.SemestarService;
import com.iktpreobuka.final_project.util.SemestarCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/semestar")
public class SemestarController {

	
	@Autowired
	private SemestarService semestarService;

	@Autowired
	SemestarCustomValidator semestarValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(semestarValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllSemestarsAdmin() {
		try {
			List<SemestarDTO> list = new ArrayList<>();
			for (Semestar semestar : semestarService.getAll()) {
				SemestarDTO semestarDTO = new SemestarDTO(semestar.getName(), semestar.getValue(), 
						semestar.getStartDate(),semestar.getEndDate(),semestar.getCode(),semestar.isActive());
				list.add(semestarDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<SemestarDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Semestars"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findBySemestarId(@PathVariable Long id) {

		try {
			Optional<Semestar> semestar = semestarService.findById(id);
			if (semestar.isPresent()) {
				SemestarDTO semestarDTO = new SemestarDTO(semestar.get().getName(),semestar.get().getValue(),semestar.get().getStartDate(),
						semestar.get().getEndDate(), semestar.get().getCode(),semestar.get().isActive());
				return new ResponseEntity<SemestarDTO>(semestarDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Semestar not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewSemestar(@Valid @RequestBody SemestarDTO newSemestar, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			semestarValidator.validate(newSemestar, result);
		}

		Semestar newSemestarEntity = new Semestar(newSemestar.getName(),newSemestar.getValue(),
				newSemestar.getStartDate(),newSemestar.getEndDate(), newSemestar.getCode(),newSemestar.isActive());

		semestarService.addNew(newSemestarEntity);

		return new ResponseEntity<>(newSemestar, HttpStatus.OK);
	}

	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateSemestar(@Valid @RequestBody SemestarDTO newSemestar,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} else {
					semestarValidator.validate(newSemestar, result);
				}

			Optional<Semestar> semestar = semestarService.findById(id);
			if (semestar.isPresent()) {
				semestar.get().setCode(newSemestar.getCode());
				semestar.get().setName(newSemestar.getName());
				semestar.get().setValue(newSemestar.getValue());
				semestar.get().setStartDate(newSemestar.getStartDate());
				semestar.get().setEndDate(newSemestar.getEndDate());
				semestar.get().setActive(newSemestar.isActive());
				

				semestarService.update(id, semestar.get());

				return new ResponseEntity<>(newSemestar, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Semestar not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteBySemestarId(@PathVariable Long id) {

		try {
			Optional<Semestar> semestar = semestarService.findById(id);
			if (semestar.isPresent()) {

				SemestarDTO semestarDTO = new SemestarDTO(semestar.get().getName(),semestar.get().getValue(),semestar.get().getStartDate(),
						semestar.get().getEndDate(),semestar.get().getCode(),semestar.get().isActive());
				semestarService.delete(id);
				return new ResponseEntity<SemestarDTO>(semestarDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Semestar not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
}

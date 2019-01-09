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
import com.iktpreobuka.final_project.entities.dto.ParentDTO;
import com.iktpreobuka.final_project.services.ParentService;
import com.iktpreobuka.final_project.util.ParentCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/parent")
public class ParentController {

	@Autowired
	private ParentService parentService;

	@Autowired
	ParentCustomValidator parentValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(parentValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllParentsPrivate() {
		try {
			List<ParentDTO> list = new ArrayList<>();
			for (Parent parent : parentService.getAllParents()) {
				ParentDTO parentDTO = new ParentDTO(parent.getName(),parent.getSurname(),parent.getEmail(),parent.getCode());
				list.add(parentDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<ParentDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Parents"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllParentsAdmin() {
		try {
			List<ParentDTO> list = new ArrayList<>();
			for (Parent parent : parentService.getAllParents()) {
				ParentDTO parentDTO = new ParentDTO(parent.getName(),parent.getSurname(),parent.getEmail(),parent.getCode());
				list.add(parentDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<ParentDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Parents"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByParentId(@PathVariable Long id) {

		try {
			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {
				ParentDTO parentDTO = new ParentDTO(parent.get().getName(),parent.get().getSurname(),parent.get().getEmail(),
						parent.get().getCode());
				return new ResponseEntity<ParentDTO>(parentDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewParent(@Valid @RequestBody ParentDTO newParent, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			parentValidator.validate(newParent, result);
		}

		Parent newParentEntity = new Parent(newParent.getName(),newParent.getSurname(),newParent.getEmail(),newParent.getCode());

		parentService.addNewParent(newParentEntity);

		return new ResponseEntity<>(newParent, HttpStatus.OK);
	}
	
	
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateParent(@Valid @RequestBody ParentDTO newParent,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} else {
					parentValidator.validate(newParent, result);
				}

			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {
				parent.get().setCode(newParent.getCode());
				parent.get().setName(newParent.getName());
				parent.get().setEmail(newParent.getEmail());
				parent.get().setSurname(newParent.getSurname());

				parentService.updateParent(id, parent.get());

				return new ResponseEntity<>(newParent, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByParentId(@PathVariable Long id) {

		try {
			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {

				ParentDTO parentDTO = new ParentDTO(parent.get().getName(),parent.get().getSurname(),parent.get().getEmail(),
						parent.get().getCode());
				parentService.deleteParent(id);
				return new ResponseEntity<ParentDTO>(parentDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
}

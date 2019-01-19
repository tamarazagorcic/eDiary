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
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.ParentDTO;
import com.iktpreobuka.final_project.entities.dto.PupilDTO;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.services.ParentService;
import com.iktpreobuka.final_project.services.PupilService;
import com.iktpreobuka.final_project.services.UserService;
import com.iktpreobuka.final_project.util.ParentCustomValidator;
import com.iktpreobuka.final_project.util.PupilCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/pupil")
public class PupilController {

	
	@Autowired
	private PupilService pupilService;

	@Autowired
	PupilCustomValidator pupilValidator;

	@Autowired
	ParentCustomValidator parentValidator;
	
	@Autowired
	private ParentService parentService;
	
	@Autowired
	private UserService userService;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(pupilValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllPupilsPrivate() {
		try {
			List<PupilDTO> list = new ArrayList<>();
			for (Pupil pupil : pupilService.getAll()) {
				Parent pt = pupil.getParent();
				ParentDTO ptDTO = new ParentDTO(pt.getName(),pt.getSurname(),pt.getCode());
				
				PupilDTO pupilDTO = new PupilDTO(pupil.getName(),pupil.getSurname(),pupil.getJmbg(),pupil.getCode(),
						ptDTO);
				list.add(pupilDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<PupilDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Pupils"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllPupilsAdmin() {
		try {
			List<PupilDTO> list = new ArrayList<>();
			for (Pupil pupil : pupilService.getAll()) {
				Parent pt = pupil.getParent();
				ParentDTO ptDTO = new ParentDTO(pt.getName(),pt.getSurname(),pt.getCode());
				
				PupilDTO pupilDTO = new PupilDTO(pupil.getName(),pupil.getSurname(),pupil.getJmbg(),pupil.getCode(),
						ptDTO);
				list.add(pupilDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<PupilDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Pupils"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByPupilId(@PathVariable Long id) {

		try {
			Optional<Pupil> pupil = pupilService.findById(id);
			if (pupil.isPresent()) {
				Parent pt = pupil.get().getParent();
				ParentDTO ptDTO = new ParentDTO(pt.getName(),pt.getSurname(),pt.getCode());
				
				PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(),pupil.get().getSurname(),
						pupil.get().getJmbg(),pupil.get().getCode(),ptDTO);
				return new ResponseEntity<PupilDTO>(pupilDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Pupil not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewPupil(@Valid @RequestBody PupilDTO newPupil, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			pupilValidator.validate(newPupil, result);
			//parentValidator.validate(newPupil.getParent(), result);
			
		}
		
		User pupilUser = new User (newPupil.getPupilUser().getEmail(),newPupil.getPupilUser().getPassword(),newPupil.getPupilUser().getUsername());

		User thisUser = userService.addNewUser(pupilUser, "pupil");
		
		ParentDTO parentDTO = newPupil.getParent();
		
		if(parentService.ifExists(parentDTO.getCode())) {
			Parent parent = parentService.findByCode(parentDTO.getCode());
			
			
			Pupil newPupilEntity = new Pupil(newPupil.getName(),newPupil.getSurname(),newPupil.getJmbg(),newPupil.getCode(),
					parent,thisUser);

			pupilService.addNew(newPupilEntity);
		}else {
			User parentUser = new User(newPupil.getParent().getParentUser().getEmail(),newPupil.getParent().getParentUser().getPassword(),
					newPupil.getParent().getParentUser().getUsername());
			
			User thisUserParent = userService.addNewUser(parentUser, "parent");
			Parent parent = new Parent(parentDTO.getName(),parentDTO.getSurname(),parentDTO.getCode(),thisUserParent);
			parentService.addNewParent(parent);
			
			Pupil newPupilE = new Pupil(newPupil.getName(),newPupil.getSurname(),newPupil.getJmbg(),newPupil.getCode(),
					parent,thisUser);
			
			pupilService.addNew(newPupilE);
			
		}
	
		
		newPupil.getParent().getParentUser().getRole().setName("parent");
		RoleDTO roleDTO = new RoleDTO(thisUser.getRole().getName());
		
		newPupil.getPupilUser().setRole(roleDTO);

		return new ResponseEntity<>(newPupil, HttpStatus.OK);
	}
	
	
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updatePupil(@Valid @RequestBody PupilDTO newPupil,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} else {
					pupilValidator.validate(newPupil, result);
				}

			Optional<Pupil> pupil = pupilService.findById(id);
			if (pupil.isPresent()) {
				pupil.get().setCode(newPupil.getCode());
				pupil.get().setName(newPupil.getName());
				pupil.get().setSurname(newPupil.getSurname());
				pupil.get().setJmbg(newPupil.getJmbg());
				ParentDTO parentDTO = newPupil.getParent();
				
				if(parentService.ifExists(parentDTO.getCode())) {
					Parent parent = parentService.findByCode(parentDTO.getCode());
					pupil.get().setParent(parent);
				}else {
					Parent parent = new Parent(parentDTO.getName(),parentDTO.getSurname(),parentDTO.getCode());
					parentService.addNewParent(parent);
					pupil.get().setParent(parent);
					
				}
				
				pupilService.update(id, pupil.get());

				return new ResponseEntity<>(newPupil, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Pupil not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByPupilId(@PathVariable Long id) {

		try {
			Optional<Pupil> pupil = pupilService.findById(id);
			if (pupil.isPresent()) {

				Parent pt = pupil.get().getParent();
				ParentDTO ptDTO = new ParentDTO(pt.getName(),pt.getSurname(),pt.getCode());
				
				PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(),pupil.get().getSurname(),pupil.get().getJmbg(),
						pupil.get().getCode(), ptDTO);
				pupilService.delete(id);
				return new ResponseEntity<PupilDTO>(pupilDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Pupil not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
}

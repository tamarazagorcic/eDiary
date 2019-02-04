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
import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.ParentDTO;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.entities.dto.UserDTO;
import com.iktpreobuka.final_project.services.ParentService;
import com.iktpreobuka.final_project.services.PupilService;
import com.iktpreobuka.final_project.services.UserService;
import com.iktpreobuka.final_project.util.ParentCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/parent")
public class ParentController {

	@Autowired
	private ParentService parentService;

		
	@Autowired
	private ParentCustomValidator parentValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PupilService pupilService;
	
//
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		
		binder.addValidators(parentValidator);
		
	}
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@Secured("ROLE_PUPIL")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllParentsPrivate() {
		try {
			List<ParentDTO> list = new ArrayList<>();
			for (Parent parent : parentService.getAllParents()) {
				ParentDTO parentDTO = new ParentDTO(parent.getId(),parent.getName(),parent.getSurname(),parent.getCode());
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
	@Secured("ROLE_PARENT")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllParentsAdmin() {
		try {
			List<ParentDTO> list = new ArrayList<>();
			for (Parent parent : parentService.getAllParents()) {
				ParentDTO parentDTO = new ParentDTO(parent.getId(),parent.getName(),parent.getSurname(),parent.getCode());
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
	//@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByParentId(@PathVariable Long id) {

		try {
			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {
				ParentDTO parentDTO = new ParentDTO(parent.get().getId(),parent.get().getName(),parent.get().getSurname(),
						parent.get().getCode());
				return new ResponseEntity<ParentDTO>(parentDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewParent(@Valid @RequestBody ParentDTO newParent, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}else{
		parentValidator.validate(newParent, result);
	}
		
		if(parentService.ifExists(newParent.getCode())) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Code for parent is present"), HttpStatus.BAD_REQUEST);
		}if(userService.ifExists(newParent.getParentUser().getUsername())) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Username for user is present"), HttpStatus.BAD_REQUEST);
		}if(userService.ifExistsEmail(newParent.getParentUser().getEmail())) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Email for user is present"), HttpStatus.BAD_REQUEST);

		}
		User parentUser = new User(newParent.getParentUser().getEmail(),newParent.getParentUser().getPassword(),
				newParent.getParentUser().getUsername());
		
		User thisUser = userService.addNewUser(parentUser, "ROLE_PARENT");
		
		Parent newParentEntity = new Parent(newParent.getName(),newParent.getSurname(),
				newParent.getCode(),thisUser );
		Parent savedParent = parentService.addNewParent(newParentEntity);
		
		RoleDTO roleDTO = new RoleDTO(thisUser.getRole().getName());
		UserDTO userDTO = new UserDTO(savedParent.getUser_id().getEmail(),savedParent.getUser_id().getUsername(),roleDTO);
		ParentDTO parentDTO = new ParentDTO(savedParent.getId(),savedParent.getName(),savedParent.getSurname(),
				savedParent.getCode(),userDTO);
		
		
		
		

		return new ResponseEntity<>(parentDTO, HttpStatus.OK);
	}
	
	
	//@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateParent(@Valid @RequestBody ParentDTO newParent,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				}

			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {
				if(!parent.get().getCode().equals(newParent.getCode())) {
					if(parentService.ifExists(newParent.getCode())) {
						return new ResponseEntity<RESTError>(new RESTError(1, "Code for parent is present"), HttpStatus.BAD_REQUEST);
					}else {
						parent.get().setCode(newParent.getCode());
					}
				}
				parent.get().setName(newParent.getName());
				parent.get().setSurname(newParent.getSurname());
				

				parentService.updateParent(id, parent.get());

				ParentDTO parentDTO = new ParentDTO(parent.get().getId(),parent.get().getName(),parent.get().getSurname(),
						parent.get().getCode());
				
				return new ResponseEntity<>(parentDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByParentId(@PathVariable Long id) {

		try {
			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {
				List<Pupil>pupils = pupilService.findPupilsByParent(parent.get());
				if(pupils.size() !=0) {
					return new ResponseEntity<RESTError>(new RESTError(1, "You can not delete parent when there are pupils in this school."), HttpStatus.BAD_REQUEST);

				}else {

					ParentDTO parentDTO = new ParentDTO(parent.get().getName(),parent.get().getSurname(),
						parent.get().getCode());
					userService.deleteUser(parent.get().getUser_id().getId());
					parentService.deleteParent(id);
					return new ResponseEntity<ParentDTO>(parentDTO, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
}

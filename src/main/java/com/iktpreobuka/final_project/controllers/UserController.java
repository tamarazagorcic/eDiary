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
import com.iktpreobuka.final_project.entities.Activity;
import com.iktpreobuka.final_project.entities.Role;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.ActivityDTO;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.entities.dto.UserDTO;
import com.iktpreobuka.final_project.services.RoleService;
import com.iktpreobuka.final_project.services.UserService;
import com.iktpreobuka.final_project.util.UserCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/user")
public class UserController {

	
	@Autowired
	private UserService userService;

	@Autowired
	UserCustomValidator userValidator;
	
	@Autowired
	private RoleService roleService;
	

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllUsersAdmin() {
		try {
			List<UserDTO> list = new ArrayList<>();
			for (User user : userService.getAllUsers()) {
				
				Role role = user.getRole();
				RoleDTO roleDTO = new RoleDTO(role.getName());
				UserDTO userDTO = new UserDTO(user.getEmail(), user.getPassword(), user.getUsername(),roleDTO);
				list.add(userDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<UserDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Subject"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByUserId(@PathVariable Long id) {

		try {
			Optional<User> user = userService.findById(id);
			if (user.isPresent()) {
				Role role = user.get().getRole();
				RoleDTO roleDTO = new RoleDTO(role.getName());

				UserDTO userDTO = new UserDTO(user.get().getEmail(), user.get().getPassword(), user.get().getUsername(),roleDTO);
				return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "User not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST) //, consumes = "application/json"
	public ResponseEntity<?> addNewUser(@Valid @RequestBody UserDTO newUser, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			userValidator.validate(newUser, result);
			
		}

		//RoleDTO roleDTO = newUser.getRole();

		if (roleService.ifExists(newUser.getRole().getName())) {

			Role role = roleService.findByName(newUser.getRole().getName());
			
				User newuserE = new User(newUser.getEmail(),newUser.getPassword(), newUser.getUsername(),
						role);

				userService.addNewUserWithoutRole(newuserE);
		
		}else {

			Role role = null;
			
			User newuserEntity = new User(newUser.getEmail(), newUser.getPassword(), newUser.getUsername(),role);

			userService.addNewUserWithoutRole(newuserEntity);
			
		}
		return new ResponseEntity<>(newUser, HttpStatus.OK);

	}
	

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO newUser,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} else {
					userValidator.validate(newUser, result);
				}

			Optional<User> user = userService.findById(id);
			if (user.isPresent()) {
				user.get().setEmail(newUser.getEmail());
				user.get().setPassword(newUser.getPassword());
				user.get().setUsername(newUser.getUsername());
				
				Role role = roleService.findByName(newUser.getRole().getName());

				user.get().setRole(role);
				userService.updateUser(id, user.get());

				return new ResponseEntity<>(newUser, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "User not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByUserId(@PathVariable Long id) {

		try {
			Optional<User> user = userService.findById(id);
			if (user.isPresent()) {

				RoleDTO roleDTO = new RoleDTO(user.get().getRole().getName());
				UserDTO userDTO = new UserDTO(user.get().getEmail(), user.get().getPassword(), user.get().getUsername(),roleDTO);
				userService.deleteUser(id);
				return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "User not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{idUser}/role/{idRole}")
	public ResponseEntity<?> matchUserWithRole(@PathVariable Long idUser, @PathVariable Long idRole) {

		try {
			Optional<User> user = userService.findById(idUser);
			Optional<Role> role = roleService.findById(idRole);
			if (user.isPresent() && role.isPresent()) {

			user.get().setRole(role.get());
			userService.updateUser(idUser, user.get());
			
			RoleDTO roleDTO = new RoleDTO(role.get().getName());
			
			UserDTO userDTO = new UserDTO(user.get().getEmail(), user.get().getPassword(), user.get().getUsername(),roleDTO);
			
				return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "User not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
}

package com.iktpreobuka.final_project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.final_project.entities.Role;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.LoginDTO;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.entities.dto.UserDTO;
import com.iktpreobuka.final_project.repositories.RoleRepository;
import com.iktpreobuka.final_project.repositories.UserRepository;

@RestController
@RequestMapping(path = "/project/auth")
public class AuthController {

	@Autowired
	  private UserRepository userRepository;

	  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	  @Autowired
	  private RoleRepository roleRepository;

//	  @CrossOrigin
//	  @RequestMapping(method = RequestMethod.POST, value = "/register")
//	  public ResponseEntity<?> Register(@Valid @RequestBody UserDTO newUser, BindingResult result) {
//	    if (result.hasErrors()) {
//	      return new ResponseEntity<String>(this.generateErrorMessage(result), HttpStatus.BAD_REQUEST);
//	    }
//	    RoleEntity role = roleRepository.findByName(EUserRole.ROLE_CUSTOMER.toString());
//	    UserEntity user = new UserEntity();
//	    user.setFirstName(newUser.getFirstName());
//	    user.setLastName(newUser.getLastName());
//	    user.setUsername(newUser.getUsername());
//	    user.setEmail(newUser.getEmail());
//	    user.setPassword(getPassEncoded(newUser.getPassword()));
//	    user.setRole(role);
//
//	    return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
//	  }

	  @CrossOrigin
	  @RequestMapping(method = RequestMethod.POST, value = "/login")
	  public ResponseEntity<?> Login(@Valid @RequestBody LoginDTO loginDto) {
	    User user = userRepository.findByUsername(loginDto.getUsername());

	    if (user == null) {
	      return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
	    } else if (!encoder.matches(loginDto.getPassword(), user.getPassword())) {
	      return new ResponseEntity<String>("Invalid credentials", HttpStatus.BAD_REQUEST);
	    }
	    
	    Role role = user.getRole();
		RoleDTO roleDTO = new RoleDTO(role.getName());
		UserDTO userDTO = new UserDTO(user.getId(),user.getEmail(),user.getPassword(), user.getUsername(),roleDTO);
		
	    return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	  }

	  private String generateErrorMessage(BindingResult result) {
	    // drugi način, bez upotrebe result.getAllErrors().stream()...
	    String msg = " ";
	    for (ObjectError error : result.getAllErrors()) {
	      msg += error.getDefaultMessage();
	      msg += " ";
	    }

	    return msg;
	  }

	  private static String getPassEncoded(String pass) {
	    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    return bCryptPasswordEncoder.encode(pass);
	  }
}

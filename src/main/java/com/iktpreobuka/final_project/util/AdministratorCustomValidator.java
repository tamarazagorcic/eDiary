package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.AdministratorDTO;

@Component
public class AdministratorCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> myClass) {
		
		return AdministratorDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdministratorDTO admin = (AdministratorDTO) target;
		
		if(admin.getCode().equals("") || admin.getCode().equals(" ")) {
			errors.reject("400", "Code for administrator must be provided.");
		}if(admin.getName().equals("") || admin.getName().equals(" ")) {
			errors.reject("400", "Name for administrator must be provided.");
		}if(admin.getSurname().equals("") || admin.getSurname().equals(" ")) {
			errors.reject("400", "Surname for administrator must be provided.");
		}if(!admin.getAdminUser().getPassword().equals(admin.getAdminUser().getConfirmPassword())) {
			errors.reject("400", "password must match.");
		}if(admin.getAdminUser().getEmail().isEmpty()) {
			errors.reject("400", "email must be provided.");
		}if(admin.getAdminUser().getUsername().isEmpty()) {
			errors.reject("400", "username must be provided.");
		}
		
	}
}

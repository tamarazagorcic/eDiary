package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.ParentDTO;

@Component
public class ParentCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> myClass) {
		
		return ParentDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ParentDTO parent = (ParentDTO) target;
		
		if(parent.getCode().equals("") || parent.getCode().equals(" ")) {
			errors.reject("400", "Code for parent must be provided.");
		}if(parent.getName().equals("") || parent.getName().equals(" ")) {
			errors.reject("400", "Name for parent must be provided.");
		}if(parent.getSurname().equals("") || parent.getSurname().equals(" ")) {
			errors.reject("400", "Surname for parent must be provided.");
		}if(!parent.getParentUser().getPassword().equals(parent.getParentUser().getConfirmPassword())) {
			errors.reject("400", "password must match.");
		}if(parent.getParentUser().getEmail().isEmpty()) {
			errors.reject("400", "email must be provided.");
		}if(parent.getParentUser().getUsername().isEmpty()) {
			errors.reject("400", "username must be provided.");
		}
		
	}
}

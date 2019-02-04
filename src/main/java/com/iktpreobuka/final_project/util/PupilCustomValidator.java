package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.PupilDTO;

@Component
public class PupilCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> myClass) {
		
		return PupilDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PupilDTO pupil = (PupilDTO) target;
		
		if(pupil.getCode().equals("") || pupil.getCode().equals(" ")) {
			errors.reject("400", "Code for pupil must be provided.");
		}if(pupil.getName().equals("") || pupil.getName().equals(" ")) {
			errors.reject("400", "Name for pupil must be provided.");
		}if(pupil.getSurname().equals("") || pupil.getSurname().equals(" ")) {
			errors.reject("400", "Surname for pupil must be provided.");
		}if(!pupil.getPupilUser().getPassword().equals(pupil.getPupilUser().getConfirmPassword())) {
			errors.reject("400", "password for pupil user must match.");
		}if(pupil.getPupilUser().getEmail().isEmpty()) {
			errors.reject("400", "email for pupil must be provided.");
		}if(pupil.getPupilUser().getUsername().isEmpty()) {
			errors.reject("400", "username for pupil must be provided.");
		}if(!pupil.getParent().getParentUser().getPassword().equals(pupil.getParent().getParentUser().getConfirmPassword())) {
			errors.reject("400", "password for parent user must match.");
		}if(pupil.getParent().getParentUser().getUsername().isEmpty()) {
			errors.reject("400", "username for parent must be provided.");
		}if(pupil.getParent().getParentUser().getEmail().isEmpty()) {
			errors.reject("400", "email for parent must be provided.");
		}
		
	}
}

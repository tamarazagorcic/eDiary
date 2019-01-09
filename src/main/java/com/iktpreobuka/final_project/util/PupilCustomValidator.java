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
		}
		
	}
}

package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.SemestarDTO;
@Component
public class SemestarCustomValidator implements Validator{

	
	@Override
	public boolean supports(Class<?> myClass) {
		
		return SemestarDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SemestarDTO semestar = (SemestarDTO) target;
		
		if(semestar.getName().equals("") || semestar.getName().equals(" ")) {
			errors.reject("400", "Name for semestar must be provided.");
		}if(semestar.getCode().equals("") || semestar.getCode().equals(" ")) {
			errors.reject("400", "Code for semestar must be provided.");
		}
		
	}
}

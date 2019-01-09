package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.SchoolClassDTO;
@Component
public class SchoolClassCustomValidator implements Validator{

	
	@Override
	public boolean supports(Class<?> myClass) {
		
		return SchoolClassDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SchoolClassDTO schoolClass = (SchoolClassDTO) target;
		
		if(schoolClass.getCode().equals("") || schoolClass.getCode().equals(" ")) {
			errors.reject("400", "Code for school class must be provided.");
		}
		
	}
}

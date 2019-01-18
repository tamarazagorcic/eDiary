package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.SubjectDTO;

@Component
public class SubjectCustomValidator implements Validator {

	@Override
	public boolean supports(Class<?> myClass) {
		
		return SubjectDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SubjectDTO subject = (SubjectDTO) target;
		
		if(subject.getCode().equals("") || subject.getCode().equals(" ")) {
			errors.reject("400", "Code for Subject must be provided.");
		}if(subject.getName().equals("") || subject.getName().equals(" ")) {
			errors.reject("400", "Name for Subject must be provided.");
		}
		
	}
}

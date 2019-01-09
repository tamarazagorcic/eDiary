package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.ProfessorDTO;

@Component
public class ProfessorCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> myClass) {
		
		return ProfessorDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProfessorDTO professor = (ProfessorDTO) target;
		
		if(professor.getCode().equals("") || professor.getCode().equals(" ")) {
			errors.reject("400", "Code for professor must be provided.");
		}if(professor.getName().equals("") || professor.getName().equals(" ")) {
			errors.reject("400", "Name for professor must be provided.");
		}if(professor.getSurname().equals("") || professor.getSurname().equals(" ")) {
			errors.reject("400", "Surname for professor must be provided.");
		}
		
	}
}

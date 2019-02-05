package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.MarkDTO;

@Component
public class MarkCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> myClass) {
		
		return MarkDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MarkDTO mark = (MarkDTO) target;
		
		
		
	}
}

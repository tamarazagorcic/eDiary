package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.ActivityDTO;


@Component
public class ActivityCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> myClass) {
		
		return ActivityDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ActivityDTO activity = (ActivityDTO) target;
		
		if(activity.getCode().equals("") || activity.getCode().equals(" ")) {
			errors.reject("400", "Code for Activity must be provided.");
		}if(activity.getName().equals("") || activity.getName().equals(" ")) {
			errors.reject("400", "Name for Activity must be provided.");
		}
		
	}
}

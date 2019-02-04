package com.iktpreobuka.final_project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.final_project.entities.dto.RoleDTO;

@Component
public class RoleCustomValidator implements Validator{


	@Override
	public boolean supports(Class<?> myClass) {
		
		return RoleDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RoleDTO role = (RoleDTO) target;
		

		if(role.getName().equals("") || role.getName().equals(" ")){
			errors.reject("400", "Name must be provided for role.");
		}
		
	}
}

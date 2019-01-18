package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.Role;

public interface RoleService {

	 Role findByName(String name);
	 boolean ifExists(String name);
	 Optional<Role> findById(Long id);
	 
}

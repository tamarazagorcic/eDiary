package com.iktpreobuka.final_project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Role;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;
	
	public Role findByName(String name) {
		
		return roleRepo.findByName(name);
	}
	
	public boolean ifExists(String name) {
		
		if(roleRepo.findByName(name) != null) {
			return true;
		}else return false;
		
		
	}
	
	
	public Optional<Role> findById(Long id) {
		return roleRepo.findById(id);
	}
}

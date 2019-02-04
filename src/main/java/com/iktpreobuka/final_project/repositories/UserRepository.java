package com.iktpreobuka.final_project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
	User findByEmail(String email);
}

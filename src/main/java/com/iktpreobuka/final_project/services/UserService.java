package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.User;

public interface UserService {

	Iterable<User> getAllUsers();
	Optional<User> findById(Long id);
	User addNewUser(User newUser, String name);
	User updateUser(Long id, User newUser);
	 User deleteUser(Long id);
	 User addNewUserWithoutRole(User newUser);
	 
}

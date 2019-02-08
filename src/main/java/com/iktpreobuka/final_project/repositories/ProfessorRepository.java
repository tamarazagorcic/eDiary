package com.iktpreobuka.final_project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.User;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {

	Professor findByCode(String code);

	//Professor findByUser_Id(User user);
}

package com.iktpreobuka.final_project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.Subject;

public interface ProfessorSubjectRepository extends CrudRepository<ProfessorSubject, Long> {

	//List<Subject>findByProfessor(Long id);
	
	
}

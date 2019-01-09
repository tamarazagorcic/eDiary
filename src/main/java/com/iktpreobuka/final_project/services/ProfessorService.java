package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.Professor;

public interface ProfessorService {

	Iterable<Professor> getAll();
	Optional<Professor> findById(Long id);
	Professor addNew(Professor newProfessor);
	Professor update(Long id, Professor newProfessor);
	Professor delete(Long id);
	
}

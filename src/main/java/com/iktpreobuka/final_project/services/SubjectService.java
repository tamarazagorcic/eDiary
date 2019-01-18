package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.Subject;

public interface SubjectService {

	Iterable<Subject> getAllSubject();
	Optional<Subject> findById(Long id);
	Subject addNewSubject(Subject newSubject);
	Subject updateSubject(Long id, Subject newSubject);
	Subject deleteSubject(Long id);
	
}

package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.Semestar;

public interface SemestarService {

	Iterable<Semestar> getAll();
	Optional<Semestar> findById(Long id);
	Semestar addNew(Semestar newSemestar);
	Semestar update(Long id, Semestar newSemestar);
	Semestar delete(Long id);
	
}

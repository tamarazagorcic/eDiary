package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.SchoolClass;

public interface SchoolClassService {


	Iterable<SchoolClass> getAll();
	Optional<SchoolClass> findById(Long id);
	SchoolClass addNew(SchoolClass newSchoolClass);
	SchoolClass update(Long id, SchoolClass newSchoolClass);
	SchoolClass delete(Long id);
}

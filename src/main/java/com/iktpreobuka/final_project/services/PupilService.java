package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.final_project.entities.Pupil;

public interface PupilService {

	Iterable<Pupil> getAll();
	Optional<Pupil> findById(Long id);
	 Pupil addNew(Pupil newPupil);
	 Pupil update(Long id, Pupil newPupil);
	 Pupil delete(Long id);
	 public List<Pupil> findPupilsByClass(Long id);
	 
}

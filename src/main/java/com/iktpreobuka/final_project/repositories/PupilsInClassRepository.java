package com.iktpreobuka.final_project.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.SchoolClass;

public interface PupilsInClassRepository extends CrudRepository<PupilsInClass, Long> {

	Optional<PupilsInClass> findByPupilAndSchoolClass(Pupil pupil, SchoolClass schoolClass);
}

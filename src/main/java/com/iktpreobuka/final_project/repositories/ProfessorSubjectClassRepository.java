package com.iktpreobuka.final_project.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.SchoolClass;

public interface ProfessorSubjectClassRepository extends CrudRepository<ProfessorSubjectClass, Long> {

	Optional<ProfessorSubjectClass> findByProfessorSubjectAndSchoolClass(ProfessorSubject professorSubject, SchoolClass schoolClass);
}

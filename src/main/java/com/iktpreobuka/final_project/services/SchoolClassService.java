package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.SchoolClass;

public interface SchoolClassService {


	Iterable<SchoolClass> getAll();
	Optional<SchoolClass> findById(Long id);
	SchoolClass addNew(SchoolClass newSchoolClass);
	SchoolClass update(Long id, SchoolClass newSchoolClass);
	SchoolClass delete(Long id);
	PupilsInClass addNewPC(Long idSC, Long idP);
	List<SchoolClass> findClassesByPupils(Long id);
	 ProfessorSubjectClass addSubjectToClass(ProfessorSubjectClass professorSubjectClass);
	 boolean ifExistsConectonSchoolClassPupil(SchoolClass sc, Pupil pupil);
	 
}

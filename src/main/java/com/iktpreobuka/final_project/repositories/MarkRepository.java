package com.iktpreobuka.final_project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.entities.PupilsInClass;

public interface MarkRepository extends CrudRepository<Mark, Long> {

	List<Mark> findByPupil(PupilsInClass pc);
}

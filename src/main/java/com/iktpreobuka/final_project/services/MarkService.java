package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.Mark;

public interface MarkService {

	 Mark deleteMark(Long id);
	 Mark updateMark(Long id, Mark newMark);
	 Mark addNewMark(Mark newMark);
	 Optional<Mark> findById(Long id);
	 Iterable<Mark> getAllMarks();
	 
}

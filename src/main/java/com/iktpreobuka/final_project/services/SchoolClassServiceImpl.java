package com.iktpreobuka.final_project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.repositories.SchoolClassRepository;

@Service
public class SchoolClassServiceImpl implements SchoolClassService{

	@Autowired
	private SchoolClassRepository scRepo;
	
	public Iterable<SchoolClass> getAll() {
		return scRepo.findAll();
	}

	
	public Optional<SchoolClass> findById(Long id) {
		
		return scRepo.findById(id);
	}

	
	public SchoolClass addNew(SchoolClass newSchoolClass) {
		
		return scRepo.save(newSchoolClass);
	}

	
	public SchoolClass update(Long id, SchoolClass newSchoolClass) {

		if (newSchoolClass == null || !scRepo.findById(id).isPresent()) {
			return null;
		}

		SchoolClass temp = scRepo.findById(id).get();

		temp.setCode(newSchoolClass.getCode());
		temp.setGrade(newSchoolClass.getGrade());
		temp.setVersion(newSchoolClass.getVersion());
		temp.setSemestar(newSchoolClass.getSemestar());
		
		
		

		return scRepo.save(temp);
	}

	
	public SchoolClass delete(Long id) {
		
		if (!scRepo.findById(id).isPresent()) {
			return null;
		}
		SchoolClass temp = scRepo.findById(id).get();
		scRepo.deleteById(id);
		return temp;
	}

}

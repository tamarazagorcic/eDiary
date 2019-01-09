package com.iktpreobuka.final_project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.repositories.ProfessorRepository;

@Service
public class ProfessorServiceImpl implements ProfessorService{

	@Autowired
	private ProfessorRepository professorRepo;
	
	public Iterable<Professor> getAll() {
		return professorRepo.findAll();
	}

	public Optional<Professor> findById(Long id) {
		return professorRepo.findById(id);
	}

	public Professor addNew(Professor newProfessor) {

		return professorRepo.save(newProfessor);
	}
	public Professor update(Long id, Professor newProfessor) {

		if (newProfessor == null || !professorRepo.findById(id).isPresent()) {
			return null;
		}

		Professor temp = professorRepo.findById(id).get();

		temp.setCode(newProfessor.getCode());
		temp.setName(newProfessor.getName());
		temp.setVersion(newProfessor.getVersion());
		temp.setSurname(newProfessor.getSurname());

		return professorRepo.save(temp);
	}

	public Professor delete(Long id) {

		if (!professorRepo.findById(id).isPresent()) {
			return null;
		}
		Professor temp = professorRepo.findById(id).get();
		professorRepo.deleteById(id);
		return temp;
	}
	
	
}

package com.iktpreobuka.final_project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.repositories.ParentRepository;

@Service
public class ParentServiceImpl implements ParentService {

	@Autowired
	private ParentRepository parentRepo;
	
	public Iterable<Parent> getAllParents() {
		return parentRepo.findAll();
	}

	public Optional<Parent> findById(Long id) {
		return parentRepo.findById(id);
	}

	public Parent addNewParent(Parent newParent) {

		return parentRepo.save(newParent);
	}
	
	public Parent updateParent(Long id, Parent newParent) {

		if (newParent == null || !parentRepo.findById(id).isPresent()) {
			return null;
		}

		Parent temp = parentRepo.findById(id).get();

		temp.setName(newParent.getName());
		temp.setSurname(newParent.getSurname());
		temp.setEmail(newParent.getEmail());
		temp.setCode(newParent.getCode());
		temp.setVersion(newParent.getVersion());
		

		return parentRepo.save(temp);
	}

	public Parent deleteParent(Long id) {

		if (!parentRepo.findById(id).isPresent()) {
			return null;
		}
		Parent temp = parentRepo.findById(id).get();
		parentRepo.deleteById(id);
		return temp;
	}
}

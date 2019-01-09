package com.iktpreobuka.final_project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.repositories.PupilRepository;

@Service
public class PupilServiceImpl implements PupilService{

	@Autowired
	private PupilRepository pupilRepo;
	
	public Iterable<Pupil> getAll() {
		return pupilRepo.findAll();
	}

	public Optional<Pupil> findById(Long id) {
		return pupilRepo.findById(id);
	}

	public Pupil addNew(Pupil newPupil) {

		return pupilRepo.save(newPupil);
	}
	
	public Pupil update(Long id, Pupil newPupil) {

		if (newPupil == null || !pupilRepo.findById(id).isPresent()) {
			return null;
		}

		Pupil temp = pupilRepo.findById(id).get();

		temp.setName(newPupil.getName());
		temp.setSurname(newPupil.getSurname());
		temp.setCode(newPupil.getCode());
		temp.setVersion(newPupil.getVersion());
		temp.setJmbg(newPupil.getJmbg());
		temp.setParent(newPupil.getParent());
		

		return pupilRepo.save(temp);
	}

	public Pupil delete(Long id) {

		if (!pupilRepo.findById(id).isPresent()) {
			return null;
		}
		Pupil temp = pupilRepo.findById(id).get();
		pupilRepo.deleteById(id);
		return temp;
	}
}

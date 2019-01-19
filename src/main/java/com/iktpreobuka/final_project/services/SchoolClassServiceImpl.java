package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.repositories.ProfessorSubjectClassRepository;
import com.iktpreobuka.final_project.repositories.ProfessorSubjectRepository;
import com.iktpreobuka.final_project.repositories.PupilRepository;
import com.iktpreobuka.final_project.repositories.PupilsInClassRepository;
import com.iktpreobuka.final_project.repositories.SchoolClassRepository;

@Service
public class SchoolClassServiceImpl implements SchoolClassService{

	@Autowired
	private SchoolClassRepository scRepo;
	
	@Autowired
	private PupilsInClassRepository pcRepo;
	
	@Autowired
	private PupilRepository pRepo;
	
	@Autowired 
	private ProfessorSubjectRepository psRepo;
	
	@Autowired
	private ProfessorSubjectClassRepository pscRepo;
	
	@Autowired
	private ProfessorService pService;
	
	
	@PersistenceContext
	private EntityManager em;
	
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
	
	public PupilsInClass addNewPC(Long idSC, Long idP) {
	
	SchoolClass tempSC = scRepo.findById(idSC).get();
	Pupil tempP = pRepo.findById(idP).get();
		
		PupilsInClass entity = new PupilsInClass(tempSC,tempP);
		return pcRepo.save(entity);
	}


	

	@SuppressWarnings("unchecked")
	
	public List<SchoolClass> findClassesByPupils(Long id) {
		
     String str = "select sc from SchoolClass sc right join fetch sc.pupils p right join fetch p.pupil pu where pu.id = :id";
		
		Query query = em.createQuery(str);
		query.setParameter("id", id);
		
		
		return query.getResultList();
		
	}

//
//	public ProfessorSubjectClass addNewSubjectsToClass(Long idP, Long idS,Long idSC) {
//		
//		ProfessorSubject temp = (ProfessorSubject) pService.fingConectionProfSubject(idP, idS);
//		
//		SchoolClass tempSC = scRepo.findById(idSC).get();
//		
//		ProfessorSubjectClass entity = new ProfessorSubjectClass(temp,tempSC);
//		return pscRepo.save(entity);
//		
//	}
	
	public ProfessorSubjectClass addSubjectToClass(ProfessorSubjectClass professorSubjectClass) {
		
		
		return pscRepo.save(professorSubjectClass);
	}

	@SuppressWarnings("static-access")
	public boolean ifExistsConectonSchoolClassPupil(SchoolClass sc, Pupil pupil) {
		Optional<PupilsInClass> pupilClass = pcRepo.findByPupilAndSchoolClass(pupil, sc);
		if( pupilClass.isPresent()) {
			return true;
		}else return false;
	}

	
}

package com.iktpreobuka.final_project.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.Activity;
import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.entities.dto.ActivityDTO;
import com.iktpreobuka.final_project.entities.dto.MarkDTO;
import com.iktpreobuka.final_project.entities.dto.ProfessorDTO;
import com.iktpreobuka.final_project.entities.dto.PupilDTO;
import com.iktpreobuka.final_project.entities.dto.SchoolClassDTO;
import com.iktpreobuka.final_project.entities.dto.SubjectDTO;
import com.iktpreobuka.final_project.models.EmailObject;
import com.iktpreobuka.final_project.services.ActivityService;
import com.iktpreobuka.final_project.services.EmailService;
import com.iktpreobuka.final_project.services.MarkService;
import com.iktpreobuka.final_project.services.ProfessorService;
import com.iktpreobuka.final_project.services.PupilService;
import com.iktpreobuka.final_project.services.SchoolClassService;
import com.iktpreobuka.final_project.services.SubjectService;
import com.iktpreobuka.final_project.util.MarkCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/mark")
public class MarkController {

	@Autowired
	private MarkService markService;
	
	@Autowired
	MarkCustomValidator markValidator;
	
	@Autowired
	private SchoolClassService scService;


	@Autowired
	private PupilService pupilService;
	
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private EmailService emailService;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(markValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	

	//@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByMarkId(@PathVariable Long id) {

		try {
			Optional<Mark> mark = markService.findById(id);
			if (mark.isPresent()) {
				
				Activity activity = mark.get().getActivity();
				ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());
				
				Professor professor = mark.get().getProfessor().getProfessorSubject().getProfessor();
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(),professor.getSurname(),professor.getCode());
				
				Subject subject = mark.get().getProfessor().getProfessorSubject().getSubject();
				SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());
				
				Pupil pupil = mark.get().getPupil().getPupil();
				PupilDTO pupilDTO = new PupilDTO(pupil.getName(),pupil.getSurname(),pupil.getCode());
				
				SchoolClass sc = mark.get().getPupil().getSchoolClass();
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade());
				

				MarkDTO markDTO = new MarkDTO(professorDTO,pupilDTO,subjectDTO,scDTO,acDTO,mark.get().getValue(),mark.get().getDate());
				return new ResponseEntity<MarkDTO>(markDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Activity not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllMarksPrivate() {
		try {
			List<MarkDTO> list = new ArrayList<>();
			for (Mark mark : markService.getAllMarks()) {
				
				Activity activity = mark.getActivity();
				ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());
				
				Professor professor = mark.getProfessor().getProfessorSubject().getProfessor();
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(),professor.getSurname(),professor.getCode());
				
				Subject subject = mark.getProfessor().getProfessorSubject().getSubject();
				SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());
				
				Pupil pupil = mark.getPupil().getPupil();
				PupilDTO pupilDTO = new PupilDTO(pupil.getName(),pupil.getSurname(),pupil.getCode());
				
				SchoolClass sc = mark.getPupil().getSchoolClass();
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade());
				

				MarkDTO markDTO = new MarkDTO(professorDTO,pupilDTO,subjectDTO,scDTO,acDTO,mark.getValue(),mark.getDate());
				
				
				
				
				list.add(markDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<MarkDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all marks"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST, value = "/{idPr}/subject/{idSu}/class/{idSC}/pupil/{idPu}")
	public ResponseEntity<?> addNewMark(@Valid @RequestBody MarkDTO newMark, BindingResult result, @PathVariable Long idPr,
			@PathVariable Long idSu,@PathVariable Long idSC,@PathVariable Long idPu) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			markValidator.validate(newMark, result);
		}
		
		Optional<Professor> professor = professorService.findById(idPr);
		Optional<Subject> subject = subjectService.findById(idSu);
		Optional<SchoolClass> sc = scService.findById(idSC);
		Optional<Pupil> pupil = pupilService.findById(idPu);
		Optional<ProfessorSubject> professorSubject = professorService.findByProfessorSubject(professor.get(), subject.get());
		Optional<ProfessorSubjectClass>professorSubjectClass = scService.findByProfessorSubjectClass(professorSubject.get(), sc.get());
		Optional<PupilsInClass> pupilsInClass = scService.findPupilsInClass(sc.get(), pupil.get());
		Activity activity = activityService.findActivityByName(newMark.getActivity().getName());
		LocalDate date = LocalDate.now();
		
		if(professor.isPresent() && subject.isPresent()&& sc.isPresent()&& pupil.isPresent() &&
				professorSubject.isPresent() && professorSubjectClass.isPresent() && pupilsInClass.isPresent()) {
			
			
			
			
			Mark newMarkEntity = new Mark(pupilsInClass.get(),professorSubjectClass.get(),newMark.getValue(),date,activity);
			markService.addNewMark(newMarkEntity);
		}

		ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());
		
		ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getName(),professor.get().getSurname(),professor.get().getCode());
		
		SubjectDTO subjectDTO = new SubjectDTO(subject.get().getName(), subject.get().getCode());
		
		PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(),pupil.get().getSurname(),pupil.get().getCode());
		
		SchoolClassDTO scDTO = new SchoolClassDTO(sc.get().getCode(), sc.get().getGrade());
		

		MarkDTO markDTO = new MarkDTO(professorDTO,pupilDTO,subjectDTO,scDTO,acDTO,newMark.getValue(),date);

		EmailObject object = new EmailObject();
		object.setTo(pupil.get().getParent().getUser_id().getEmail());
		object.setSubject("Obavestenje o oceni Vaseg deteta " + pupil.get().getName()+ " " + pupil.get().getSurname());
		String text = "Vase dete "+ pupil.get().getName()+ " " + pupil.get().getSurname() + " je dobilo ocenu " + newMark.getValue() +
				" iz predmeta " + subject.get().getName() + " ocenio profesor " + professor.get().getName() +" " + 
				professor.get().getSurname() + " . Za dalje informacije mozete kontaktirati mail skole .";
		
		object.setText(text);
		
		if(object == null || object.getTo() == null || object.getText() == null) {
			return null;
			}
			
			emailService.sendSimpleMessage(object);
			
		
		return new ResponseEntity<>(markDTO, HttpStatus.OK);
	}
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateMark(@Valid @RequestBody MarkDTO newMark,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} else {
					markValidator.validate(newMark, result);
				}

			Optional<Mark> mark = markService.findById(id);
			if (mark.isPresent()) {
				mark.get().setValue(newMark.getValue());
				Activity activity = activityService.findActivityByName(newMark.getActivity().getName());
				mark.get().setActivity(activity);

				markService.updateMark(id, mark.get());
				
				
				ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());
				
				Professor professor = mark.get().getProfessor().getProfessorSubject().getProfessor();
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(),professor.getSurname(),professor.getCode());
				
				Subject subject = mark.get().getProfessor().getProfessorSubject().getSubject();
				SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());
				
				Pupil pupil = mark.get().getPupil().getPupil();
				PupilDTO pupilDTO = new PupilDTO(pupil.getName(),pupil.getSurname(),pupil.getCode());
				
				SchoolClass sc = mark.get().getPupil().getSchoolClass();
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade());
				

				MarkDTO markDTO = new MarkDTO(professorDTO,pupilDTO,subjectDTO,scDTO,acDTO,mark.get().getValue(),mark.get().getDate());

				return new ResponseEntity<>(markDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Activity not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		try {
			Optional<Mark> mark = markService.findById(id);
			if (mark.isPresent()) {

				Activity activity = mark.get().getActivity();
				ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());
				
				Professor professor = mark.get().getProfessor().getProfessorSubject().getProfessor();
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(),professor.getSurname(),professor.getCode());
				
				Subject subject = mark.get().getProfessor().getProfessorSubject().getSubject();
				SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());
				
				Pupil pupil = mark.get().getPupil().getPupil();
				PupilDTO pupilDTO = new PupilDTO(pupil.getName(),pupil.getSurname(),pupil.getCode());
				
				SchoolClass sc = mark.get().getPupil().getSchoolClass();
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade());
				

				MarkDTO markDTO = new MarkDTO(professorDTO,pupilDTO,subjectDTO,scDTO,acDTO,mark.get().getValue(),mark.get().getDate());
				
				markService.deleteMark(id);
				return new ResponseEntity<MarkDTO>(markDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Activity not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}

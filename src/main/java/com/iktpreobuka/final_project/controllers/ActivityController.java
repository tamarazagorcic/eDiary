package com.iktpreobuka.final_project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.iktpreobuka.final_project.entities.dto.ActivityDTO;
import com.iktpreobuka.final_project.services.ActivityService;
import com.iktpreobuka.final_project.util.ActivityCustomValidator;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/activity")
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@Autowired
	ActivityCustomValidator activityValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(activityValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	@Secured("admin")
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	public ResponseEntity<?> getAllActivitiesPublic() {
		try {
			List<ActivityDTO> list = new ArrayList<>();
			for (Activity activity : activityService.getAllActivities()) {
				ActivityDTO activityDTO = new ActivityDTO(activity.getName(), activity.getCode());
				list.add(activityDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<ActivityDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Activities"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured("admin")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllActivitiesPrivate() {
		try {
			List<ActivityDTO> list = new ArrayList<>();
			for (Activity activity : activityService.getAllActivities()) {
				ActivityDTO activityDTO = new ActivityDTO(activity.getName(), activity.getCode());
				list.add(activityDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<ActivityDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Activities"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllActivitiesAdmin() {
		try {
			List<ActivityDTO> list = new ArrayList<>();
			for (Activity activity : activityService.getAllActivities()) {
				ActivityDTO activityDTO = new ActivityDTO(activity.getName(), activity.getCode());
				list.add(activityDTO);
			}
			if (list.size() != 0) {
				return new ResponseEntity<Iterable<ActivityDTO>>(list, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Activities"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByActivityId(@PathVariable Long id) {

		try {
			Optional<Activity> activity = activityService.findById(id);
			if (activity.isPresent()) {
				ActivityDTO activityDTO = new ActivityDTO(activity.get().getName(), activity.get().getCode());
				return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Activity not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewActivity(@Valid @RequestBody ActivityDTO newActivity, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			activityValidator.validate(newActivity, result);
		}

		Activity newActivityEntity = new Activity(newActivity.getName(), newActivity.getCode());

		activityService.addNewActivity(newActivityEntity);

		return new ResponseEntity<>(newActivity, HttpStatus.OK);
	}

	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateActivity(@Valid @RequestBody ActivityDTO newActivity,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} else {
					activityValidator.validate(newActivity, result);
				}

			Optional<Activity> activity = activityService.findById(id);
			if (activity.isPresent()) {
				activity.get().setCode(newActivity.getCode());
				activity.get().setName(newActivity.getName());

				activityService.updateActivity(id, activity.get());

				return new ResponseEntity<>(newActivity, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Activity not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("admin")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByActivityId(@PathVariable Long id) {

		try {
			Optional<Activity> activity = activityService.findById(id);
			if (activity.isPresent()) {

				ActivityDTO activityDTO = new ActivityDTO(activity.get().getName(), activity.get().getCode());
				activityService.deleteActivity(id);
				return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Activity not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

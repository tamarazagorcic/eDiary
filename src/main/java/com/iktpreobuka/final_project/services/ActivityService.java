package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.Activity;

public interface ActivityService {

	Iterable<Activity> getAllActivities();
	Optional<Activity> findById(Long id);
	Activity addNewActivity(Activity newActivity);
	Activity updateActivity(Long id, Activity newActivity);
	Activity deleteActivity(Long id);
}

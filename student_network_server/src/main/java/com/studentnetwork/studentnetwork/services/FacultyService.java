package com.studentnetwork.studentnetwork.services;

import java.util.List;

import com.studentnetwork.studentnetwork.model.Faculity;


public interface FacultyService {
	List<Faculity> getAllFacultys();	
	Faculity getFacultyById(String id);
	
}

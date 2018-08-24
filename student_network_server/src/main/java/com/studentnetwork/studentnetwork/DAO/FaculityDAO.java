package com.studentnetwork.studentnetwork.DAO;

import java.util.List;

import com.studentnetwork.studentnetwork.model.Faculity;

public interface FaculityDAO
{
	List<Faculity> getFaculitiesByOrgID(String orgID);
	List<Faculity> getFaculitiesByFacultyID(String facultyID);
	Faculity addFaculity(Faculity faculity);
}

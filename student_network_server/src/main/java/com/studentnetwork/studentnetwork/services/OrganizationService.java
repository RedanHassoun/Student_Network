package com.studentnetwork.studentnetwork.services;

import java.util.List;

import com.studentnetwork.studentnetwork.model.Faculity;
import com.studentnetwork.studentnetwork.model.Organization;

public interface OrganizationService 
{
	List<Organization> getAllOrganizations();	
	Organization getOrgById(String id);
	List<Faculity> getFaculitiesByOrgID(String orgID);
}

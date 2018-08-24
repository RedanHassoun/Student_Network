package com.studentnetwork.studentnetwork.DAO;

import java.util.List;
import com.studentnetwork.studentnetwork.model.Organization;

public interface OrganizatonsDAO 
{
	List<Organization> getAllOrganizations();
	Organization getOrgById(String id);
	Organization addOrganization(Organization organization);
}

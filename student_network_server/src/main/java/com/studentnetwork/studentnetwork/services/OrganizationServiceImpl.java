package com.studentnetwork.studentnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.studentnetwork.studentnetwork.DAO.FaculityDAO;
import com.studentnetwork.studentnetwork.DAO.OrganizatonsDAO;
import com.studentnetwork.studentnetwork.DAO.UserDao;
import com.studentnetwork.studentnetwork.model.Faculity;
import com.studentnetwork.studentnetwork.model.Organization;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService
{
	 @Autowired
	 OrganizatonsDAO orgDAO;
	 
	 @Autowired
	 FaculityDAO faculityDao;
	 
	@Override
	public List<Organization> getAllOrganizations() 
	{
		return orgDAO.getAllOrganizations();
	}
	@Override
	public Organization getOrgById(String id) {
		return orgDAO.getOrgById(id);
	}
	@Override
	public List<Faculity> getFaculitiesByOrgID(String orgID) {
		return faculityDao.getFaculitiesByOrgID(orgID);
	}
	
}

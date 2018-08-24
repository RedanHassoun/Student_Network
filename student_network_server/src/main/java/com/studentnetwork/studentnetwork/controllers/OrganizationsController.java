package com.studentnetwork.studentnetwork.controllers;

import java.util.ArrayList;
import java.util.List;

import com.studentnetwork.studentnetwork.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.studentnetwork.studentnetwork.model.Faculity;
import com.studentnetwork.studentnetwork.model.FaculityMessage;
import com.studentnetwork.studentnetwork.model.Organization;
import com.studentnetwork.studentnetwork.model.OrganizationsMessage;

@RestController
public class OrganizationsController 
{
	@Autowired
	OrganizationService orgService;
	
	 
	
	@RequestMapping(value="/getAllOrgs", method=RequestMethod.GET)
	public @ResponseBody OrganizationsMessage getAllOrgs()
	{
		List<Organization> theList = orgService.getAllOrganizations();
		 
		OrganizationsMessage toReturn = new OrganizationsMessage();
		toReturn.setOrgs(theList);
		
		return toReturn;
	}
	
	@RequestMapping(value="/getFaculitiesByOrgID/{orgID}", method=RequestMethod.GET)
	public @ResponseBody FaculityMessage getFaculitiesByOrgID(@PathVariable String orgID)
	{
		List<Faculity> faculities = orgService.getFaculitiesByOrgID(orgID);
		FaculityMessage toReturn = new FaculityMessage();
		toReturn.setFacList(faculities);
		return toReturn;
	}
}

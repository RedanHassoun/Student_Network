package com.studentnetwork.studentnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.studentnetwork.studentnetwork.DAO.FaculityDAO;
import com.studentnetwork.studentnetwork.DAO.OrganizatonsDAO;
import com.studentnetwork.studentnetwork.model.Faculity;
import org.springframework.stereotype.Service;

@Service
public class FacultyServiceImpl implements FacultyService{

	
	 
	 @Autowired
	 FaculityDAO faculityDao;
	@Override
	public List<Faculity> getAllFacultys() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Faculity getFacultyById(String id) {
		System.out.println("acultyServiceImpl");
		List<Faculity> list=faculityDao.getFaculitiesByFacultyID(id);
		
		return list.get(0);
	}

}

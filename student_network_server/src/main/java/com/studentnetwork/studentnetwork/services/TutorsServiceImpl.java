package com.studentnetwork.studentnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.studentnetwork.studentnetwork.DAO.TutorDAO;
import com.studentnetwork.studentnetwork.model.CourseMessage;
import com.studentnetwork.studentnetwork.model.MessageListTutors;
import com.studentnetwork.studentnetwork.model.OrgFriendsList;
import com.studentnetwork.studentnetwork.model.Tutor;
import com.studentnetwork.studentnetwork.model.TutorLikeEntry;
import com.studentnetwork.studentnetwork.model.TutorRequest;
import com.studentnetwork.studentnetwork.model.TutorRequestList;
import com.studentnetwork.studentnetwork.model.UserMessage;
import org.springframework.stereotype.Service;

@Service
public class TutorsServiceImpl implements TutorsService
{
	@Autowired
	TutorDAO tutorDAO;
	
	@Override
	public MessageListTutors getAllTutoresByORG(String orgID,int index)
	{
		System.out.println("reached tutors service");
		return tutorDAO.getAllTutoresByORG(orgID, index);
	}

	@Override
	public CourseMessage getAllTutoredCourses(String tutorID, int index)
	{
		return tutorDAO.getAllTutoredCourses(tutorID, index);
	}

	@Override
	public OrgFriendsList getAllTutoredStudents(String tutodID, int index) {
		return tutorDAO.getAllTutoredStudents(tutodID, index);
	}

	@Override
	public UserMessage getTutorByID(String tutorID) {
		UserMessage tutorToReturn = tutorDAO.getTutorByID(tutorID);
		int numLikes = tutorDAO.getNumLikesForTutor(tutorID);
		tutorToReturn.setNumLikes(numLikes);
		return tutorToReturn;
	}

	@Override
	public TutorRequestList getRequestsForTutor(String tutorId,int index) { 
		return tutorDAO.getRequestsForTutor(tutorId, index);
	}

	@Override
	public void insertTutor(Tutor tutor) {
		tutorDAO.insertTutor(tutor);
	}

	@Override
	public void insertTutorRequest(TutorRequest tutorRequest) {
		tutorDAO.insertTutorRequest(tutorRequest);
		
	}

	@Override
	public void updateTutorRequest(TutorRequest tutorRequest) {
		tutorDAO.updateTutorRequest(tutorRequest);
		
	}

	@Override
	public void likeTutor(TutorLikeEntry tutorLikeEntry) {
		tutorDAO.likeTutor(tutorLikeEntry);
	}

	@Override
	public void updateTutorProfile(Tutor tutor) {
		tutorDAO.updateTutorProfile(tutor);
		
	}

	@Override
	public boolean isRequestPending(TutorRequest tutorRequest) {
		return tutorDAO.isRequestPending(tutorRequest);
	}
}

package com.studentnetwork.studentnetwork.DAO;

import com.studentnetwork.studentnetwork.model.CourseMessage;
import com.studentnetwork.studentnetwork.model.MessageListTutors;
import com.studentnetwork.studentnetwork.model.OrgFriendsList;
import com.studentnetwork.studentnetwork.model.Tutor;
import com.studentnetwork.studentnetwork.model.TutorLikeEntry;
import com.studentnetwork.studentnetwork.model.TutorRequest;
import com.studentnetwork.studentnetwork.model.TutorRequestList;
import com.studentnetwork.studentnetwork.model.UserMessage;

public interface TutorDAO 
{
	MessageListTutors getAllTutoresByORG(String orgID,int index);
	CourseMessage getAllTutoredCourses(String tutorID,int index);
	OrgFriendsList getAllTutoredStudents(String tutodID,int index);
	UserMessage getTutorByID(String tutorID);
	TutorRequestList getRequestsForTutor(String tutorId,int index);
	void insertTutor(Tutor tutor);
	void insertTutorRequest(TutorRequest tutorRequest);
	void updateTutorRequest(TutorRequest tutorRequest);
	void likeTutor(TutorLikeEntry tutorLikeEntry);
	int getNumLikesForTutor(String tutorId);
	public void updateTutorProfile(Tutor tutor);
	boolean isRequestPending(TutorRequest tutorRequest);
}

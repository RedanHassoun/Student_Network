package com.studentnetwork.studentnetwork.controllers;

import java.util.List;

import com.studentnetwork.studentnetwork.constants.AppConstants;
import com.studentnetwork.studentnetwork.services.CourseService;
import com.studentnetwork.studentnetwork.services.TutorsService;
import com.studentnetwork.studentnetwork.services.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.studentnetwork.studentnetwork.model.Course;
import com.studentnetwork.studentnetwork.model.CourseMessage;
import com.studentnetwork.studentnetwork.model.MessageListTutors;
import com.studentnetwork.studentnetwork.model.OrgFriendsList;
import com.studentnetwork.studentnetwork.model.Status;
import com.studentnetwork.studentnetwork.model.Tutor;
import com.studentnetwork.studentnetwork.model.TutorLikeEntry;
import com.studentnetwork.studentnetwork.model.TutorRequest;
import com.studentnetwork.studentnetwork.model.TutorRequestList;
import com.studentnetwork.studentnetwork.model.UserMessage;

@RestController
public class TutorsController 
{
	@Autowired
	TutorsService tutorsService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/getAllTutorsByORG/{orgID}/{index}", method=RequestMethod.GET)
	public @ResponseBody MessageListTutors getAllTutorsByORG(@PathVariable String orgID,@PathVariable int index)
	{  
		System.out.println("recieved organization id =" + orgID);
		try{
			return tutorsService.getAllTutoresByORG(orgID, index);
		}catch(Exception ex){
			return null;
		}
	}
	
	@RequestMapping(value="/getTutoredCourses/{tutorID}/{index}", method=RequestMethod.GET)
	public @ResponseBody CourseMessage getTutoredCourses(@PathVariable String tutorID,@PathVariable int index)
	{  
		try{
			return tutorsService.getAllTutoredCourses(tutorID, index);
		}catch(Exception ex){
			return null;
		} 
	}
	
	@RequestMapping(value="/getTutoredStudents/{tutorID}/{index}", method=RequestMethod.GET)
	public @ResponseBody OrgFriendsList getTutoredStudents(@PathVariable String tutorID,@PathVariable int index)
	{  
		System.out.println("@@@ GETTING TUTORED STUDENTTS ");
		try{
			return tutorsService.getAllTutoredStudents(tutorID, index);  
		}catch(Exception ex){
			return null;
		} 
	}
 
	
	@RequestMapping(value="/getTutorByID/{tutorID}", method=RequestMethod.GET)
	public @ResponseBody UserMessage getTutorByID(@PathVariable String tutorID)
	{  
		System.out.println("##### ID = "+tutorID);
		try{
			return tutorsService.getTutorByID(tutorID);
		}catch(Exception ex){
			return null;
		}
	}
	
	
	@RequestMapping(value="/getRequestsForTutor/{tutorID}/{index}", method=RequestMethod.GET)
	public @ResponseBody TutorRequestList getRequestsForTutor(@PathVariable String tutorID,@PathVariable int index)
	{   
		System.out.println("Got request from "+tutorID);
		try{
			return tutorsService.getRequestsForTutor(tutorID,index);
		}catch(Exception ex){
			return null;
		}
	}
	
	
	/**
	 * Get a tutor request as a POST and add the corresponding tutor with his coures 
	 */
	@RequestMapping(value="/addTutor", method=RequestMethod.POST)
	public @ResponseBody Status addTutor(@RequestBody Tutor tutor)
	{ 
		Status status = new Status();
		status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
		
		try{  
			tutorsService.insertTutor(tutor); 
		}
		catch(Exception ex)
		{ 
			ex.printStackTrace();
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
			return status;
		} 
		
		
		try{  
			List<Course> listCourses = tutor.getCourses(); 
			courseService.addCoursesForTutor(listCourses, Integer.parseInt(tutor.getId()));
		}
		catch(Exception ex)
		{ 
			ex.printStackTrace();
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
			return status;
		} 
		
		
		return status;
	}
	
	/**
	 * This method get a tutor request as a POST, adds it to the tutor requests table 
	 * and sends a notificaiton to the tutor - TODO 
	 */
	@RequestMapping(value="/likeTutor", method=RequestMethod.POST)
	public @ResponseBody Status likeTutor(@RequestBody TutorLikeEntry tutorLikeEntry)
	{  
		Status status = new Status();
	
		try{
			tutorsService.likeTutor(tutorLikeEntry);
			status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
			return status;
		}catch (Exception e) {
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
			return status;
		}
	}
	
	
	/**
	 * This method get a tutor request as a POST, adds it to the tutor requests table 
	 * and sends a notificaiton to the tutor - TODO 
	 */
	@RequestMapping(value="/makeTutorRequest", method=RequestMethod.POST)
	public @ResponseBody Status makeTutorRequest(@RequestBody TutorRequest tutorRequest)
	{ 
		// TODO - should check if there is a same request that is already pending
		Status status = new Status();
		status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
		
		if(tutorsService.isRequestPending(tutorRequest)){
			status.setStatus(AppConstants.ConnectionConstants.STATUS_ALREADY_PENDING);
			return status;
		}
		
		
		try{
			tutorsService.insertTutorRequest(tutorRequest);
		}catch (Exception e) {
			e.printStackTrace();
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
		}
		
		
		try{
			sendPushNotificationForTutor(tutorRequest);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO - cant send push 
		}
		
		return status;
	}
	
	private void sendPushNotificationForStudent(TutorRequest tutorRequest)
	{
		/* Get tutor from database */
		UserMessage tutor = userService.getUserById(""+tutorRequest.getTutorId());
		
		/* Create the message to be sent */
		UserMessage student = userService.getUserById(""+tutorRequest.getStudentId());
		Course course = courseService.getCourseByID(""+tutorRequest.getCourseId());
		String message;
		  
		JSONObject data = new JSONObject();
		data.put(AppConstants.ConnectionConstants.JSON_KEY_TUTOR_ID, tutor.getUserId());
		data.put(AppConstants.ConnectionConstants.JSON_KEY_COURSE_NAME, course.getName());
		
		if(tutorRequest.getStatus() == AppConstants.ConnectionConstants.REQUEST_ACCEPTED){
			data.put(AppConstants.ConnectionConstants.JSON_KEY_IS_ACCEPTED, AppConstants.ConnectionConstants.ACCEPTED_YES);
			message = ""+tutor.getUsername()+" "+AppConstants.GeneralStrings.ACCEPTED_YOUR_TUTOR_REQUEST_ON+course.getName();
		}
		else{
			data.put(AppConstants.ConnectionConstants.JSON_KEY_IS_ACCEPTED, AppConstants.ConnectionConstants.ACCEPTED_NO);
			message = ""+tutor.getUsername()+" "+AppConstants.GeneralStrings.REJECTED_YOUR_TUTOR_REQUEST_ON+course.getName();
		} 
		
		userService.sendFirebasePushNotification(message, student.getFirebaseToken(),data);
	}
	
	
	private void sendPushNotificationForTutor(TutorRequest tutorRequest) {
		/* Get tutor from database */
		UserMessage tutor = userService.getUserById(""+tutorRequest.getTutorId());
		
		/* Create the message to be sent */
		UserMessage student = userService.getUserById(""+tutorRequest.getStudentId());
		Course course = courseService.getCourseByID(""+tutorRequest.getCourseId());
		String message = ""+student.getUsername()+AppConstants.GeneralStrings.REQUESTED_TUTORING_ON+course.getName();
		
		userService.sendFirebasePushNotification(message, tutor.getFirebaseToken(),null);
	}

	/**
	 * This method is responsible for updating a specific tutor request. 
	 * the request is identified by : tutor_id,student_id,course_id,time
	 * and sends a notificaiton to the tutor - TODO 
	 */
	@RequestMapping(value="/updateTutorRequest", method=RequestMethod.POST)
	public @ResponseBody Status updateTutorRequest(@RequestBody TutorRequest tutorRequest)
	{ 
		Status status = new Status();
		status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
		
		try{
			tutorsService.updateTutorRequest(tutorRequest); 
			
		}catch (Exception e) {
			e.printStackTrace();
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
		}
		
		try{
			sendPushNotificationForStudent(tutorRequest);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	/**
	 * This method is responsible for updating the tutor profile 
	 */
	@RequestMapping(value="/updateTutorProfile", method=RequestMethod.POST)
	public @ResponseBody Status updateTutorProfile(@RequestBody Tutor tutor)
	{ 
		Status status = new Status();
		status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
		
		try{
			tutorsService.updateTutorProfile(tutor);
		}catch (Exception e) {
			e.printStackTrace();
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
		}
		
		return status;
	}
	
	
	/**
	 * TODO - temp 
	 * @param
	 * @return
	 */
	@RequestMapping(value="/mockPush/{message}/{token}", method=RequestMethod.GET)
	public @ResponseBody void mockPush(@PathVariable String message,@PathVariable String token)
	{ 
		try{
			userService.sendFirebasePushNotification(message, token,null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	
	
}

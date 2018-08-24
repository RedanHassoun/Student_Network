package com.studentnetwork.studentnetwork.controllers;

import com.studentnetwork.studentnetwork.constants.AppConstants;
import com.studentnetwork.studentnetwork.services.FacultyService;
import com.studentnetwork.studentnetwork.services.OrganizationService;
import com.studentnetwork.studentnetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.studentnetwork.studentnetwork.model.Faculity;
import com.studentnetwork.studentnetwork.model.OrgFriendsList;
import com.studentnetwork.studentnetwork.model.Organization;
import com.studentnetwork.studentnetwork.model.Status;
import com.studentnetwork.studentnetwork.model.StatusAuthenticate;
import com.studentnetwork.studentnetwork.model.UserMessage;

@RestController
public class UserController
{
	@Autowired
	UserService userService;
	 
	 @Autowired
	 FacultyService facultyService;
	 
	@Autowired
	OrganizationService organizationService;
	
		 
	@RequestMapping(value="/getllPeopleForOrg", method=RequestMethod.POST)
	public @ResponseBody OrgFriendsList getFriendOrg(@RequestBody UserMessage user)
	{
		
		OrgFriendsList orgFriendsToReturn=new OrgFriendsList(); 
		try
		{		 
			orgFriendsToReturn.setOrgFriendsList(userService.getUserList(user.getOgranizationId()));
			orgFriendsToReturn.setStatus(AppConstants.ConnectionConstants.GET_ORG_FRIENDS_COMPLETED_SUCCESFULLY);
					
			return orgFriendsToReturn;	
		}
		catch(Exception ex)
		{
			orgFriendsToReturn.setStatus(AppConstants.ConnectionConstants.GET_ORG_FRIENDS_FAILD);
			return orgFriendsToReturn;
		}
	}
	
	@RequestMapping(value="/getUserByMail", method=RequestMethod.POST)
	public @ResponseBody UserMessage getUserByMail(@RequestBody UserMessage user)
	{
		String mail = user.getEmail();
		UserMessage userToReturn = userService.getUser(mail);
		
		Organization org = organizationService.getOrgById(userToReturn.getOgranizationId());
		userToReturn.setOrganizationName(org.getName());
		
		//System.out.println(facultyService.getFacultyById(userInDB.getFacilityId()).getName());
		userToReturn.setFacilityName(((Faculity)facultyService.getFacultyById(userToReturn.getFacilityId())).getName());
		
		return userToReturn;
	}
	 
	 
	 /**
	  * Login a user using Google, by adding the user to the main users table (if necessary) then 
	  * adding to google table (if necessary) , and returning an appropriate message 
	  * @param newGoogleUser
	  * @return
	  */
	 @RequestMapping(value="/loginGoogle", method=RequestMethod.POST)
	 public @ResponseBody StatusAuthenticate loginGoogle(@RequestBody UserMessage newGoogleUser)
	 {
		StatusAuthenticate status = new StatusAuthenticate();
		
		UserMessage userInMainDB =null;

		try{
			userInMainDB = userService.getUser(newGoogleUser.getEmail());

			if(userInMainDB!=null)
			{
				UserMessage googleUSer = userService.getGoogleUser(userInMainDB.getUserId());
				
				if(googleUSer == null){

					/*
					 * Insert the user
				     */
					
					newGoogleUser.setUserId(userInMainDB.getUserId());
					
					
					userService.insertToGoogle(newGoogleUser);
				}
				
			}
		}catch (Exception e) {

			e.printStackTrace();
			return null;
		}
		status.setStatus(AppConstants.ConnectionConstants.LOGIN_OK);
		fillStatusDetails(status, userInMainDB); 
		System.out.println("###### TEST FAC ID = "+status.getFaculityID());
		
		return status;	
	 }
	 
	 
	 /**
	  * Login a user using Facebook, by adding the user to the main users table (if necessary) then 
	  * adding to facebook table (if necessary) , and returning an appropriate message 
	  * @param
	  * @return
	  */
	 @RequestMapping(value="/loginFacebook", method=RequestMethod.POST)
	 public @ResponseBody StatusAuthenticate loginFacebook(@RequestBody UserMessage newFacebookUser)
	 {
		 StatusAuthenticate status = new StatusAuthenticate();
			
			UserMessage userInMainDB =null;

			try{
				userInMainDB = userService.getUser(newFacebookUser.getEmail());

				if(userInMainDB!=null)
				{
					UserMessage googleUSer = userService.getFacebookUser(userInMainDB.getUserId());
					
					if(googleUSer == null){

						/*
						 * Insert the user
					     */
						
						newFacebookUser.setUserId(userInMainDB.getUserId());
						
						
						userService.insertToFacebook(newFacebookUser);
					}
					
				}
			}catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			status.setStatus(AppConstants.ConnectionConstants.LOGIN_OK);
			fillStatusDetails(status, userInMainDB); 
			System.out.println("###### TEST FAC ID = "+status.getFaculityID());
			
			return status;	
	 }
	 
	
	
	/**
	 * Get the user as a POST message from the client, try to login by checking the email 
	 * and password and return an appropriate status 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody StatusAuthenticate loginUser(@RequestBody UserMessage user)
	{
		System.out.println("####### LOGIN");
		StatusAuthenticate status = new StatusAuthenticate();
		
		AppConstants.UserDatabaseStatus statusInDB = checkThisUser(user);
		
		switch(statusInDB){
		case USER_NOT_EXIST:
			status.setStatus(AppConstants.ConnectionConstants.LOGIN_USER_NOT_EXIST);
			break;
		case USER_OK:
			status.setStatus(AppConstants.ConnectionConstants.LOGIN_OK);
			fillStatusDetails(status,user); 
			System.out.println("TUTOR STATUS = "+status.isTutor());
			
			break;
		case WRONG_PASSWORD:
			status.setStatus(AppConstants.ConnectionConstants.LOGIN_WRONG_PASSWORD);
			break;
		}
		
		return status;	
	}
	
	private synchronized void fillStatusDetails(StatusAuthenticate status,UserMessage user) {
		UserMessage userInDB = userService.getUser(user.getEmail());
		System.err.println("User from DB: "+userInDB);
		status.setFullName(userInDB.getFullName());
		status.setId(userInDB.getUserId());
		status.setImageURL(userInDB.getImageURL());
		status.setMail(userInDB.getEmail());
		status.setPassword(userInDB.getPassword());
		Organization org = organizationService.getOrgById(userInDB.getOgranizationId());
		status.setOrgName(org.getName());
		
		//System.out.println(facultyService.getFacultyById(userInDB.getFacilityId()).getName());
		status.setFacilityName(((Faculity)facultyService.getFacultyById(userInDB.getFacilityId())).getName());
		status.setOrgName(org.getName());
		status.setUsername(userInDB.getUsername());
		status.setAbout(userInDB.getAbout());
		status.setOgranizationId(userInDB.getOgranizationId());
		status.setFaculityID(userInDB.getFacilityId());
		status.setTutor(userInDB.isTutor());
	}

	/**
	 * This method gets a user and checks his status , one of the three : 
	 *  -> Doesn't exist
	 *  -> Have a wrong password
	 *  -> Exists with a correct password
	 * @param user
	 * @return
	 */
	private AppConstants.UserDatabaseStatus checkThisUser(UserMessage user)
	{
		UserMessage userInDB = userService.getUser(user.getEmail());
		
		if(userInDB == null)
			return AppConstants.UserDatabaseStatus.USER_NOT_EXIST;
		else
		{
			if(userInDB.getPassword().equalsIgnoreCase(user.getPassword()))
				return AppConstants.UserDatabaseStatus.USER_OK;
			else
				return AppConstants.UserDatabaseStatus.WRONG_PASSWORD;
		}
	}
	private AppConstants.UserDatabaseStatus checkThisUserBYiD(UserMessage user)
	{
		UserMessage userInDB = userService.getUserById(user.getUserId());
		
		if(userInDB == null)
			return AppConstants.UserDatabaseStatus.USER_NOT_EXIST;
		else
		{
			if(userInDB.getPassword().equalsIgnoreCase(user.getPassword()))
				return AppConstants.UserDatabaseStatus.USER_OK;
			else
				return AppConstants.UserDatabaseStatus.WRONG_PASSWORD;
		}
	}
	
	
	/**
	 * Get the user as a POST message from the client, try to register it to the database and
	 * return the appropriate message
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/registerUser", method=RequestMethod.POST)
	public @ResponseBody StatusAuthenticate registerUser(@RequestBody UserMessage user)
	{
		StatusAuthenticate status = new StatusAuthenticate();
		status.setRegisterType(user.getLoginMethod());
		 
		try{
			if(checkThisUser(user).equals(AppConstants.UserDatabaseStatus.USER_NOT_EXIST))
			{
				 userService.insertData(user);
				 status.setStatus(AppConstants.ConnectionConstants.REGISTER_STATIS_OK);
				 fillStatusDetails(status, user); 
				 return status;
			}
			else
			{
				/*
				 * Already exists 
				 * */
				status.setStatus(AppConstants.ConnectionConstants.REGISTER_STATUS_EXIST);
				return status;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			status.setStatus(AppConstants.ConnectionConstants.REGISTER_UNKNOWN_ERROR);
			return status;
		} 
		
	}
	
	/**
	 * Get the user as a POST message from the client, try to update it to the database and
	 * return the appropriate message
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/updateUserDetails", method=RequestMethod.POST)
	public @ResponseBody Status updateUser(@RequestBody UserMessage user)
	{
		Status status = new Status(); 
		
		try
		{  
			userService.updateData(user);
			status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
			return status;
		}
		catch(Exception ex)
		{ 
			ex.printStackTrace();
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
			return status;
		} 
		
	}
	
	/**
	 * This method updates the user's profile image URL 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/updateImageURL", method=RequestMethod.POST)
	public @ResponseBody Status updateImageURL(@RequestBody UserMessage user)
	{	
		Status status = new Status();
		
		try{
			userService.updateUserImageURL(user);
			status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
		}catch (Exception e) {
			e.printStackTrace();
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
		}
		
		return status;
		
	}
	
	
	/**
	 * Get the user as a POST message from the client, try to register it to the database and
	 * return the appropriate message
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/checkUserStatus", method=RequestMethod.POST)
	public @ResponseBody StatusAuthenticate checkUserStatus(@RequestBody UserMessage user)
	{	
		UserMessage userInDB = userService.getUser(user.getEmail());
		StatusAuthenticate statusUser = new StatusAuthenticate();
		
		if(userInDB == null)
			statusUser.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_EXIST);
		else
		{
			fillStatusDetails(statusUser, user); 
			if(userInDB.getPassword().equalsIgnoreCase(user.getPassword()))
				statusUser.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
			else
				statusUser.setStatus(AppConstants.ConnectionConstants.STATUS_WRONG_PASSWORD);
		}
		
		return statusUser;
		
	}
	
	
	@RequestMapping(value="/updateFirebaseToken", method=RequestMethod.POST)
	public @ResponseBody Status updateFirebaseToken(@RequestBody UserMessage user)
	{ 
		System.out.println("Updating firebase token for : "+user.getUserId()+", token = "+user.getFirebaseToken());
		Status status = new Status();
		status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
		try{
			userService.updateFirebaseToken(user);
		}catch(Exception ex){
			ex.printStackTrace();
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
		}
		return status;
	} 
	 
}

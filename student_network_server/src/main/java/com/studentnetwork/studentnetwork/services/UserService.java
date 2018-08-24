 
package com.studentnetwork.studentnetwork.services;

import java.util.List;

import org.json.simple.JSONObject;

import com.studentnetwork.studentnetwork.model.TutorLikeEntry;
import com.studentnetwork.studentnetwork.model.UserMessage;

public interface UserService
{
	 public void insertData(UserMessage user);
	 public List<UserMessage> getUserList();
	 public void deleteData(String id);
	 public UserMessage getUser(String email);
	 public UserMessage getStatusFacebook(String platformId);
	 public void updateData(UserMessage user);
	 public UserMessage getGoogleUser(String id);
	 public void insertToGoogle(UserMessage user);
	 public void insertToFacebook(UserMessage user);
	 public UserMessage getUserById(String id);
	 public List<UserMessage> getUserList(String ogranizationId );
	 public void updateFirebaseToken(UserMessage userMessage);
	 void sendFirebasePushNotification(String message,String token,JSONObject data);
	 public UserMessage getFacebookUser(String id); 
	 void updateUserImageURL(UserMessage user);
}
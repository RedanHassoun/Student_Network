package com.studentnetwork.studentnetwork.DAO;

import java.util.List;
 
import com.studentnetwork.studentnetwork.model.UserMessage;

public interface UserDao {
	 public void insertData(UserMessage user);
	 public void inserToGoogle(UserMessage user);
	 public void insertToFacebook(UserMessage user);
	 public UserMessage getGoogleUser(String id);
	 public List<UserMessage> getUserList();
	 public void updateData(UserMessage user);
	 public void deleteData(String id);
	 public UserMessage getUserByMail(String email);
	 public UserMessage getStatusFacebook(String platformId);
	 public UserMessage getUserById(String id);
	 public List<UserMessage> getUserList(String ogranizationId);
	 public void updateFirebaseToken(UserMessage userMessage);
	 public UserMessage getFacebookUser(String id);
	 void updateUserImageURL(UserMessage user);
}

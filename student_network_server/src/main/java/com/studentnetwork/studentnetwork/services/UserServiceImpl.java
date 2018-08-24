package com.studentnetwork.studentnetwork.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;
import com.studentnetwork.studentnetwork.DAO.UserDao;
import com.studentnetwork.studentnetwork.constants.AppConstants;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.studentnetwork.studentnetwork.model.UserMessage;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService 
{
	 @Autowired
	 UserDao userdao;

	@Override
	public void insertData(UserMessage user) {
		userdao.insertData(user);
	}

	@Override
	public List<UserMessage> getUserList() {
		 return userdao.getUserList();
	}

	@Override
	public void deleteData(String id) {
		 userdao.deleteData(id);
		
	}

	@Override
	public UserMessage getUser(String email) {
		System.err.println("USER_SERVICE: getting user by mail: "+email);
		return userdao.getUserByMail(email);
	}
	@Override
	public UserMessage getStatusFacebook(String platformId)
	{
		return userdao.getStatusFacebook(platformId);
	}
	@Override
	public void updateData(UserMessage user) {
		 userdao.updateData(user);
	}

	@Override
	public UserMessage getGoogleUser(String id) {
		return userdao.getGoogleUser(id);
	}
	
	@Override
	public UserMessage getFacebookUser(String id) {
		return userdao.getFacebookUser(id);
	} 

	@Override
	public void insertToGoogle(UserMessage user) {
		userdao.inserToGoogle(user);
		
	}
	@Override
	public void insertToFacebook(UserMessage user) {
		userdao.insertToFacebook(user);
		
	}


	@Override
	public UserMessage getUserById(String id) {
		return userdao.getUserById(id);
	}

	@Override
	public List<UserMessage> getUserList(String ogranizationId) {
		 return userdao.getUserList(ogranizationId);
	}

	@Override
	public void updateFirebaseToken(UserMessage userMessage) {
		userdao.updateFirebaseToken(userMessage);
	}

	@Override
	public void sendFirebasePushNotification(String message, String token,JSONObject data)
	{
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(AppConstants.URL_FIREBASE_SINGLE_NOTIFICATION);
			postRequest.addHeader(AppConstants.FIREBASE_AUTHORIZATION_KEY, "key="+AppConstants.SERVER_KEY_FIREBASE);
			JSONObject jsonOb = new JSONObject();
			JSONObject dataJsonObject = new JSONObject();
			dataJsonObject.put("body", message);
			jsonOb.put("notification", dataJsonObject); 
			jsonOb.put("to", token);
			if(data != null)
				jsonOb.put("data", data);
			

			System.out.println("^^^^^^^^^^^ SENDING JSON : "+jsonOb.toJSONString());
			
			StringEntity input = new StringEntity(jsonOb.toJSONString());
			input.setContentType("application/json"); 
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);
			
			System.out.println("########## Firebase response cose = "+response.getStatusLine().getStatusCode());

			BufferedReader br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }

	}

	@Override
	public void updateUserImageURL(UserMessage user) {
		userdao.updateUserImageURL(user);
	}

	
}
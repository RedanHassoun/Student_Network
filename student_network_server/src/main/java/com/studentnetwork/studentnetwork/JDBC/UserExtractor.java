package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.model.UserMessage;

public class UserExtractor implements ResultSetExtractor<UserMessage> 
{
	 public UserMessage extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException 
	 { 
		 UserMessage user = new UserMessage();
		 System.err.println("Extracting user");
		 user.setUserId(""+resultSet.getInt(1));
		 user.setUsername(resultSet.getString(2));
		 user.setEmail(resultSet.getString(3));
		 user.setPassword(resultSet.getString(4));
		 user.setOgranizationId(resultSet.getString(5));
		 user.setFullName(resultSet.getString(6));
		 user.setAbout(resultSet.getString(7));
		 user.setImageURL(resultSet.getString(8));
		 user.setFacilityId(resultSet.getString(9));
		 user.setFirebaseToken(resultSet.getString(10));
		 System.err.println("User Extracted");
		 return user;
	 }
}
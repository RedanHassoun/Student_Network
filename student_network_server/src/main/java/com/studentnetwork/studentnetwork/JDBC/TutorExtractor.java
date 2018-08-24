package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.model.UserMessage;

public class TutorExtractor implements ResultSetExtractor<UserMessage> 
{
	 public UserMessage extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException 
	 { 
		 UserMessage tutor = new UserMessage();
	  
		 tutor.setUserId(resultSet.getString(1));
		 tutor.setUsername(resultSet.getString(2));
		 tutor.setEmail(resultSet.getString(3));
		 tutor.setPassword(resultSet.getString(4));
		 tutor.setOgranizationId(resultSet.getString(5));
		 tutor.setFullName(resultSet.getString(6));
		 tutor.setAbout(resultSet.getString(7));
		 tutor.setImageURL(resultSet.getString(8));
		 tutor.setFacilityId(resultSet.getString(9));
	
		 return tutor;
	 }
}
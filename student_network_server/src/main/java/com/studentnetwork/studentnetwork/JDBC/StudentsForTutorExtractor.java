package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.model.UserMessage;

public class StudentsForTutorExtractor implements ResultSetExtractor<UserMessage> 
{
	 public UserMessage extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException 
	 { 
		 UserMessage studForTutor = new UserMessage();
	   
		 studForTutor.setUserId(resultSet.getString(1));
		 studForTutor.setUsername(resultSet.getString(2));
		 studForTutor.setEmail(resultSet.getString(3));
		 studForTutor.setPassword(resultSet.getString(4));
		 studForTutor.setOgranizationId(resultSet.getString(5));
		 studForTutor.setFullName(resultSet.getString(6));
		 studForTutor.setAbout(resultSet.getString(7));
		 studForTutor.setImageURL(resultSet.getString(8));
		 studForTutor.setFacilityId(resultSet.getString(9));
		 studForTutor.setDatedStartedToBeTutored(resultSet.getTimestamp(14));
		 studForTutor.setTutoredCourseName(resultSet.getString(15));
		  
		 
		 return studForTutor;
	 }
}
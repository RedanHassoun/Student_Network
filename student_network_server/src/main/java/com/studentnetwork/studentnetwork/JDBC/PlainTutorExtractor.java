package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.model.UserMessage;

public class PlainTutorExtractor implements ResultSetExtractor<UserMessage> 
{ 
 
	public UserMessage extractData(ResultSet resultSet) throws SQLException,
	   															DataAccessException 
	{ 
		UserMessage tutor = new UserMessage(); 
		tutor.setUserId(resultSet.getString(1));
		tutor.setCommunicationStr(resultSet.getString(2));
		tutor.setAboutTutor(resultSet.getString(3));
		tutor.setDatedStartedToBeTutored(resultSet.getTimestamp(4));
		try{

			tutor.setUsername(resultSet.getString(5));
			tutor.setImageURL(resultSet.getString(6));
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return tutor;
	}
}
package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.model.UserMessage;

public class GoogleUserExtractor implements ResultSetExtractor<UserMessage> 
{
	 public UserMessage extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException 
	 { 
		 UserMessage user = new UserMessage();
	  
		 user.setImageURL(resultSet.getString(2));
		 user.setUserId(""+resultSet.getInt(3));
	  
		 return user;
	 }
}
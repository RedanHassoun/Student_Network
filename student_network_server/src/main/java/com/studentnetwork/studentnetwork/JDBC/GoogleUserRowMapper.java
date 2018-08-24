package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studentnetwork.studentnetwork.model.UserMessage;

public class GoogleUserRowMapper implements RowMapper<UserMessage>
{
	 @Override
	 public UserMessage mapRow(ResultSet resultSet, int line) throws SQLException 
	 {
		 GoogleUserExtractor userExtractor = new GoogleUserExtractor();
		 return userExtractor.extractData(resultSet);
	 }

}
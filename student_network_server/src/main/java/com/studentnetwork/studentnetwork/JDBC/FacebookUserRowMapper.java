package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studentnetwork.studentnetwork.model.UserMessage;

public class FacebookUserRowMapper implements RowMapper<UserMessage>{
	@Override
	 public UserMessage mapRow(ResultSet resultSet, int line) throws SQLException 
	 {
		FacebookUserExtractor userExtractor = new FacebookUserExtractor();
		 return userExtractor.extractData(resultSet);
	 }
}

package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studentnetwork.studentnetwork.model.TutorRequest;
import com.studentnetwork.studentnetwork.model.UserMessage;

public class TutorRequestRowMapper implements RowMapper<TutorRequest>
{
	 @Override
	 public TutorRequest mapRow(ResultSet resultSet, int line) throws SQLException 
	 {
		 TutorRequestExtractor tutorRequestExtractor = new TutorRequestExtractor();
		 return tutorRequestExtractor.extractData(resultSet);
	 }

}
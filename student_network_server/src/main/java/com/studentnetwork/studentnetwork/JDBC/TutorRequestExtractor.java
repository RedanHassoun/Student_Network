package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.model.TutorRequest;

public class TutorRequestExtractor implements ResultSetExtractor<TutorRequest> 
{
	 public TutorRequest extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException 
	 { 
		 TutorRequest request = new TutorRequest(); 
		 
		 request.setTutorId(resultSet.getInt(1));
		 request.setStudentId(resultSet.getInt(2));
		 request.setCourseId(resultSet.getInt(3));
		 request.setStatus(resultSet.getInt(4));
		 request.setTime(resultSet.getTimestamp(5));
		 request.setStudentName(resultSet.getString(6));
		 request.setUserImageURL(resultSet.getString(7));
		 request.setCourseName(resultSet.getString(8));
		 
	  
		 return request;
	 }
}
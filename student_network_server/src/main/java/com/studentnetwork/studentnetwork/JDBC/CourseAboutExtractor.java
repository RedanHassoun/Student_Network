package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.model.CourseAbout;

public class CourseAboutExtractor implements ResultSetExtractor<CourseAbout> 
{ 
 
	public CourseAbout extractData(ResultSet resultSet) throws SQLException,
	   															DataAccessException 
	{ 
		CourseAbout course = new CourseAbout();
	 
		course.setPoints(resultSet.getDouble(1));
		course.setDescription(resultSet.getString(2)); 
		course.setTeacherName(resultSet.getString(3));
		course.setCourseId(resultSet.getString(4));
		course.setId(resultSet.getString(5));
		course.setCourseName(resultSet.getString(6));
		course.setFaculityName(resultSet.getString(7));
		
		return course;
	}
}
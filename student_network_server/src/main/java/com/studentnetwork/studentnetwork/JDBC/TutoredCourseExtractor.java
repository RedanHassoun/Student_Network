package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.studentnetwork.studentnetwork.model.Course;

public class TutoredCourseExtractor implements ResultSetExtractor<Course> 
{ 
 
	public Course extractData(ResultSet resultSet) throws SQLException,
	   															DataAccessException 
	{ 
		Course course = new Course();
		course.setId(resultSet.getString(1));
		course.setName(resultSet.getString(2));
		course.setFaculityID(resultSet.getString(3));
		return course;
	}
}
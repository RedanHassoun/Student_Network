package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studentnetwork.studentnetwork.model.CourseAbout;

public class CourseAboutRowMapper implements RowMapper<CourseAbout>
{
	 @Override
	 public CourseAbout mapRow(ResultSet resultSet, int line) throws SQLException 
	 {
		 CourseAboutExtractor courseExtractor = new CourseAboutExtractor();
		 return courseExtractor.extractData(resultSet);
	 }

}
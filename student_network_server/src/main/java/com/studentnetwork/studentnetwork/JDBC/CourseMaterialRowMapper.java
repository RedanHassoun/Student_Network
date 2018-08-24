package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studentnetwork.studentnetwork.model.CourseAbout;
import com.studentnetwork.studentnetwork.model.CourseMaterial;

public class CourseMaterialRowMapper implements RowMapper<CourseMaterial>
{
	 @Override
	 public CourseMaterial mapRow(ResultSet resultSet, int line) throws SQLException 
	 {
		 CourseMaterialExtractor courseExtractor = new CourseMaterialExtractor();
		 return courseExtractor.extractData(resultSet);
	 }

}
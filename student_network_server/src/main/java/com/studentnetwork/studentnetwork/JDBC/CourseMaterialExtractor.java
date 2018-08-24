package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.studentnetwork.studentnetwork.model.CourseMaterial;

public class CourseMaterialExtractor implements ResultSetExtractor<CourseMaterial> 
{ 
 
	public CourseMaterial extractData(ResultSet resultSet) throws SQLException,
	   															DataAccessException 
	{ 
		CourseMaterial course = new CourseMaterial();
	 
		course.setTitle(resultSet.getString(1));
		course.setImageURL(resultSet.getString(2));
		course.setCourseId(resultSet.getString(3));
		course.setExternalURL(resultSet.getString(4));
		
		return course;
	}
}
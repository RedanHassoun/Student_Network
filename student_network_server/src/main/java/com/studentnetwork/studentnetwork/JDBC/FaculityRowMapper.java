package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studentnetwork.studentnetwork.model.Faculity;
import com.studentnetwork.studentnetwork.model.Organization;

public class FaculityRowMapper implements RowMapper<Faculity>
{
	 @Override
	 public Faculity mapRow(ResultSet resultSet, int line) throws SQLException 
	 {
		 FaculityExtractor facExtractor = new FaculityExtractor();
		 return facExtractor.extractData(resultSet);
	 }

}
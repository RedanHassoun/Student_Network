package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.model.Faculity;

public class FaculityExtractor implements ResultSetExtractor<Faculity> 
{
	 public Faculity extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException 
	 { 
		 Faculity faculity = new Faculity();
	   
		 faculity.setId(""+resultSet.getInt(1));
		 
		 faculity.setName(resultSet.getString(2));
		 
		 faculity.setOrgID(""+resultSet.getInt(3)); 
		  
	
		 return faculity;
	 }
}
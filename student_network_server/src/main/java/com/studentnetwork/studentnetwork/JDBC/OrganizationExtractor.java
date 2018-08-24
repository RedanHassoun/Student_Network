package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.model.Organization;

public class OrganizationExtractor implements ResultSetExtractor<Organization> 
{
	 public Organization extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException 
	 { 
		 Organization org = new Organization();
		 org.setId(""+resultSet.getInt(1));
		 org.setName(resultSet.getString(2));
		 org.setType(resultSet.getString(3));
		 String longitude = resultSet.getString(4);
		 org.setLongitude(Double.parseDouble(longitude));
		 String latitude = resultSet.getString(5);
		 org.setLatitude(Double.parseDouble(latitude));
		 return org;
	 }
}
package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studentnetwork.studentnetwork.model.Organization;
import com.studentnetwork.studentnetwork.model.UserMessage;

public class OrganizationRowMapper implements RowMapper<Organization>
{
	 @Override
	 public Organization mapRow(ResultSet resultSet, int line) throws SQLException 
	 {
		 OrganizationExtractor orgExtractor = new OrganizationExtractor();
		 return orgExtractor.extractData(resultSet);
	 }

}
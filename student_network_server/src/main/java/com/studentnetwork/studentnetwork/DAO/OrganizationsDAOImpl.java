package com.studentnetwork.studentnetwork.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.studentnetwork.studentnetwork.JDBC.CourseRowMapper;
import com.studentnetwork.studentnetwork.constants.AppConstants;
import com.studentnetwork.studentnetwork.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.studentnetwork.studentnetwork.JDBC.GoogleUserRowMapper;
import com.studentnetwork.studentnetwork.JDBC.OrganizationRowMapper;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.GoogleUsersTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.OrganizationsTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.SqlQuery;
import com.studentnetwork.studentnetwork.model.Organization;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationsDAOImpl implements OrganizatonsDAO
{
	
	@Autowired
	DataSource dataSource;


	@Override
	public List<Organization> getAllOrganizations()
	{
		List userList = new ArrayList();

		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  userList = jdbcTemplate.query(SqlQuery.SQL_ORGANIZATION_GET_ALL, new OrganizationRowMapper());
		  return userList;
	}


	@Override
	public Organization getOrgById(String id) {
		List<Organization> organization = null;
		String sql = "select * from "+OrganizationsTable.TABLE_NAME+" where "+OrganizationsTable.COLUMN_NAME_ID+"= '" + id+"'";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		organization = jdbcTemplate.query(sql, new OrganizationRowMapper());
		  
		try{
			  return organization.get(0);
		 }catch(Exception ex){
			  return null;
		}
	}

	@Override
	public Organization addOrganization(Organization organization) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(
					AppConstants.SqlQuery.SQL_INSERT_ORGANIZATION,
					new Object[] { organization.getName(),
								   organization.getType(),
							       organization.getLongitude(),
							       organization.getLatitude()});
			System.err.println("Added");
			List<Organization> organizationList = jdbcTemplate.query(
									AppConstants.SqlQuery.SQL_GET_ORGANIZATION_BY_NAME+
									organization.getName()+"'",
									new OrganizationRowMapper());
			return  organizationList.get(0);
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

}

package com.studentnetwork.studentnetwork.DAO;

import java.util.List;

import javax.sql.DataSource;

import com.studentnetwork.studentnetwork.JDBC.CourseRowMapper;
import com.studentnetwork.studentnetwork.JDBC.FaculityRowMapper;
import com.studentnetwork.studentnetwork.constants.AppConstants;
import com.studentnetwork.studentnetwork.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.studentnetwork.studentnetwork.model.Faculity;
import org.springframework.stereotype.Repository;

@Repository
public class FaculityDAOImpl implements FaculityDAO
{
	@Autowired
	DataSource dataSource;
	
	@Override
	public List<Faculity> getFaculitiesByOrgID(String orgID)
	{ 
		List<Faculity> facList = null;
		String sql = AppConstants.SqlQuery.SQL_SELECT_FACILITIES_BY_ORG+orgID;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
		facList = jdbcTemplate.query(sql, new FaculityRowMapper());
		  
		try{ 
			  return facList;
		 }catch(Exception ex){ 
			  return null;
		}
	}
	
	public List<Faculity> getFaculitiesByFacultyID(String facultyID)
	{ 
		List<Faculity> facList;
		String sql = AppConstants.SqlQuery.SQL_SELECT_FACILITIE_BY_ID+facultyID;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
		facList = jdbcTemplate.query(sql, new FaculityRowMapper()); 
		  
		try{ 
			  return facList;
		 }catch(Exception ex){ 
			System.out.println("FaculityDAOImpl");
			  return null;
		}
	}

	@Override
	public Faculity addFaculity(Faculity faculity) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(
					AppConstants.SqlQuery.SQL_INSERT_FACILITY,
					new Object[] { faculity.getId(),
								   faculity.getName(),
								   faculity.getOrgID()});

			List<Faculity> faculityList = jdbcTemplate.query(
							AppConstants.SqlQuery.SQL_SELECT_FACILITIE_BY_NAME+
									faculity.getName()+"'",
									new FaculityRowMapper());
			return  faculityList.get(0);
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

}

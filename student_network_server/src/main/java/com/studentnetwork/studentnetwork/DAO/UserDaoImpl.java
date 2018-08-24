package com.studentnetwork.studentnetwork.DAO;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.studentnetwork.studentnetwork.JDBC.FacebookUserRowMapper;
import com.studentnetwork.studentnetwork.JDBC.GoogleUserRowMapper;
import com.studentnetwork.studentnetwork.JDBC.PlainTutorExtractor;
import com.studentnetwork.studentnetwork.JDBC.PlainTutorRowMapper;
import com.studentnetwork.studentnetwork.JDBC.UserRowMapper;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.FacebookUsersTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.GoogleUsersTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.UsersTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.SqlQuery;
import com.studentnetwork.studentnetwork.model.UserMessage;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao
{
	@Autowired
	DataSource dataSource;

	@Override
	public void insertData(UserMessage user)
	{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
			SqlQuery.SQL_INSERT_USER,
		    new Object[] { user.getUsername(), user.getEmail(),
		    			   user.getPassword(), user.getOgranizationId() ,
		    			   user.getFullName() , user.getAbout(),
		    			   user.getImageURL(),user.getFacilityId() });
		
	}

	@Override
	public List<UserMessage> getUserList()
	{
		 List<UserMessage> userList = new ArrayList<UserMessage>();

		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  userList = jdbcTemplate.query(SqlQuery.SQL_GET_ALL_USERS, new UserRowMapper());
		  return userList;
	}

	@Override
	public void updateData(UserMessage user) 
	{
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  System.out.println(user.getUserId());
		  jdbcTemplate.update(
			SqlQuery.SQL_UPDATE_USER,
		    new Object[] { user.getPassword(),user.getFullName() , user.getAbout(),user.getUsername(), user.getUserId() });  
	}

	@Override
	public void deleteData(String id) 
	{
		  String sql = SqlQuery.SQL_DELETE_USER+ id;
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  jdbcTemplate.update(sql);	
	}
	@Override
	public UserMessage getStatusFacebook(String platformId)
	{
		  try
		  {
			  List<UserMessage> userList = new ArrayList<UserMessage>(); 
			  String sql = "select * from "+FacebookUsersTable.TABLE_NAME+" where "+FacebookUsersTable.COLUMN_NAME_FACEBOOK_ID+"= '" + platformId+"'"; 
			  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
			  userList = jdbcTemplate.query(sql, new FacebookUserRowMapper()); 
			  UserMessage userToReturn = userList.get(0); 
			  
			  
			  return userToReturn;
		  }
		  catch(Exception ex)
		  {
			  return null;
		  }
		  
	}
	@Override
	public UserMessage getUserByMail(String email) 
	{
		System.err.println("USER_DAO: getting user by mail:"+email);
		  try
		  {
			  List<UserMessage> userList;
			  String sql = "select * from "+UsersTable.TABLE_NAME+" where "+
					  UsersTable.COLUMN_NAME_EMAIL+"= '" + email+"'";
			  System.err.println("USER_DAO: executing query:"+ sql);
			  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
			  userList = jdbcTemplate.query(sql, new UserRowMapper()); 
			  UserMessage userToReturn = userList.get(0); 
			  
			  try{
				  String tutorSQL = SqlQuery.SQL_GET_TUTOR_BY_ID + userToReturn.getUserId()+"'"; 
				  List<UserMessage> tutorList = jdbcTemplate.query(tutorSQL, new PlainTutorRowMapper()); 
				  
				  if(tutorList.size()> 0)
					  userToReturn.setTutor(true);
				   
				  
				  
			  }catch (Exception e) {
				  e.printStackTrace(); 
				  // in this case the user is not a tutor 
			}
			  
			  return userToReturn;
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  return null;
		  }
		  
	}

	@Override
	public void inserToGoogle(UserMessage user)
	{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		// TODO temp ///
		System.out.println("****** id= "+user.getUserId()+" url = "+user.getImageURL());
		///////////////////////
		jdbcTemplate.update(
			SqlQuery.SQL_INSER_TO_GOOGLE,
		    new Object[] { user.getImageURL(), user.getUserId() , user.getDomainId() });
	}

	@Override
	public void insertToFacebook(UserMessage user)
	{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		// TODO temp ///
		System.out.println("****** id= "+user.getUserId()+" DomainId = "+user.getDomainId() );
		///////////////////////
		jdbcTemplate.update(
			SqlQuery.SQL_INSER_TO_FACEBOOK,
		    new Object[] { user.getUserId() , user.getDomainId() });
	}

	@Override
	public UserMessage getGoogleUser(String id) 
	{
		List<UserMessage> userList = new ArrayList<UserMessage>();
		String sql = "select * from "+GoogleUsersTable.TABLE_NAME+" where "+GoogleUsersTable.COLUMN_NAME_USER_ID+"= '" + id+"'";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userList = jdbcTemplate.query(sql, new GoogleUserRowMapper());
		  
		try{
			  return userList.get(0);
		 }catch(Exception ex){
			  return null;
		}
	}
	
	@Override
	public UserMessage getFacebookUser(String id) {
		List<UserMessage> userList = new ArrayList<UserMessage>();
		String sql = "select * from "+GoogleUsersTable.TABLE_NAME+" where "+FacebookUsersTable.COLUMN_NAME_USER_ID+"= '" + id+"'";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userList = jdbcTemplate.query(sql, new FacebookUserRowMapper());
		  
		try{
			  return userList.get(0);
		 }catch(Exception ex){
			  return null;
		}
	}

	@Override
	public UserMessage getUserById(String id) 
	{
		List<UserMessage> userList = new ArrayList<UserMessage>();
		  String sql = "select * from "+UsersTable.TABLE_NAME+" where "+UsersTable.COLUMN_NAME_USER_ID+"= '" + id+"'";
		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  userList = jdbcTemplate.query(sql, new UserRowMapper());
		  
		  try
		  {
			  return userList.get(0);
		  }
		  catch(Exception ex)
		  {
			  return null;
		  }
	}

	@Override
	public List<UserMessage> getUserList(String ogranizationId) {
		List<UserMessage> userList = new ArrayList<UserMessage>();

		  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		  String sql =SqlQuery.SQL_GET_ALL_USERS_BY_ORG+ogranizationId;
		  userList = jdbcTemplate.query(sql, new UserRowMapper());
		  return userList;
	}

	@Override
	public void updateFirebaseToken(UserMessage userMessage) {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(SqlQuery.SQL_UPDATE_FIREBASE_TOKEN, 
									userMessage.getFirebaseToken(), userMessage.getUserId());
	}

	@Override
	public void updateUserImageURL(UserMessage user) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(SqlQuery.SQL_UPDATE_USER_IMAGE, 
										user.getImageURL(),user.getUserId());
	} 

}

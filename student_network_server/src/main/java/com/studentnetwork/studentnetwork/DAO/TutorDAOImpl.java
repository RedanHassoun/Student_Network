package com.studentnetwork.studentnetwork.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.studentnetwork.studentnetwork.JDBC.PlainTutorRowMapper;
import com.studentnetwork.studentnetwork.JDBC.StudentsForTutorRowMapper;
import com.studentnetwork.studentnetwork.JDBC.TutorRequestRowMapper;
import com.studentnetwork.studentnetwork.JDBC.TutorRowMapper;
import com.studentnetwork.studentnetwork.JDBC.TutoredCourseRowMapper;
import com.studentnetwork.studentnetwork.constants.AppConstants;
import com.studentnetwork.studentnetwork.constants.AppConstants.ConnectionConstants;
import com.studentnetwork.studentnetwork.constants.AppConstants.SqlQuery;
import com.studentnetwork.studentnetwork.model.Course;
import com.studentnetwork.studentnetwork.model.CourseMessage;
import com.studentnetwork.studentnetwork.model.MessageListTutors;
import com.studentnetwork.studentnetwork.model.OrgFriendsList;
import com.studentnetwork.studentnetwork.model.Tutor;
import com.studentnetwork.studentnetwork.model.TutorLikeEntry;
import com.studentnetwork.studentnetwork.model.TutorRequest;
import com.studentnetwork.studentnetwork.model.TutorRequestList;
import com.studentnetwork.studentnetwork.model.UserMessage;
import org.springframework.stereotype.Repository;

@Repository
public class TutorDAOImpl implements TutorDAO
{
	@Autowired
	DataSource dataSource;
	
	@Override
	public MessageListTutors getAllTutoresByORG(String orgID,int index)
	{
		System.out.println("reached data access object");
		// TODO - implement index 
		List<UserMessage> userList;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = SqlQuery.SQL_GET_TUTORS_BY_ORG + orgID;
		userList = jdbcTemplate.query(sql, new TutorRowMapper());
		MessageListTutors toReturn =new MessageListTutors();
		toReturn.setListTutors(userList);
		return toReturn;
	}

	@Override
	public CourseMessage getAllTutoredCourses(String tutorID, int index) 
	{
		try{
			System.out.println("reached data access object");
			// TODO - implement index 
			List<Course> coursesList;

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = SqlQuery.SQL_GET_TUTORED_COURSES + tutorID;
			coursesList = jdbcTemplate.query(sql, new TutoredCourseRowMapper());
			CourseMessage toReturn =new CourseMessage();
			toReturn.setCourses(coursesList); 
			return toReturn;
		}catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public OrgFriendsList getAllTutoredStudents(String tutodID, int index)
	{
		try{
			System.out.println("reached data access object");
			// TODO - implement index 
			List<UserMessage> usersList;
			System.out.println("####  1");
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			System.out.println("####  2");
			String sql = SqlQuery.SQL_GET_TUTORED_STUDENTS + tutodID;
			System.out.println("####  3");
			usersList = jdbcTemplate.query(sql, new StudentsForTutorRowMapper());
			System.out.println("####  4");
			System.out.println("list size = "+usersList.size());
			System.out.println("####  5");
			OrgFriendsList toReturn =new OrgFriendsList();
			System.out.println("####  6");
			toReturn.setOrgFriendsList(usersList);
			System.out.println("####  7");
			return toReturn;
		}catch(Exception ex)
		{ // TODO - set status
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public UserMessage getTutorByID(String tutorID) 
	{
		List<UserMessage> userList = new ArrayList<UserMessage>();
		String sql = SqlQuery.SQL_GET_TUTOR_BY_ID + tutorID+"'";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userList = jdbcTemplate.query(sql, new PlainTutorRowMapper());
		
		try
		{
			UserMessage tutorToReturn = userList.get(0);
			
			return tutorToReturn;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public TutorRequestList getRequestsForTutor(String tutorId,int index)
	{
		try{ 
			// TODO - implement index 
			List<TutorRequest> tutorRequests;

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = SqlQuery.SQL_GET_REQUESTS_FOR_TUTOR + "'"+tutorId+"'";
			tutorRequests = jdbcTemplate.query(sql, new TutorRequestRowMapper());
			TutorRequestList toReturn =new TutorRequestList();
			toReturn.setRequestList(tutorRequests); 
			return toReturn;
		}catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public void insertTutor(Tutor tutor) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
			SqlQuery.SQL_INSERT_TUTOR,
		    new Object[] { tutor.getId(),tutor.getCommunicationStr(),tutor.getAbout() });
	}

	@Override
	public void insertTutorRequest(TutorRequest tutorRequest) {
		
		// TODO - tutor status should have a defualt value on the database 
		tutorRequest.setStatus(0); // TODO - temp 
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
			SqlQuery.SQL_INSERT_TUTOR_REQUEST,
		    new Object[] { tutorRequest.getTutorId(),tutorRequest.getStudentId(),tutorRequest.getCourseId(),tutorRequest.getStatus() });
		
	}

	@Override
	public void updateTutorRequest(TutorRequest tutorRequest) {
		int tutorID = tutorRequest.getTutorId();
		int studentID = tutorRequest.getStudentId();
		int courseID = tutorRequest.getCourseId();
		int status = tutorRequest.getStatus();
		 
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(
				SqlQuery.SQL_UPDATE_TUTOR_REQUEST, 
				status, tutorID,studentID,courseID,tutorRequest.getTime());
		
		System.out.println("inserting tutored student....");
		if(status == AppConstants.ConnectionConstants.REQUEST_ACCEPTED){
			 
			try{
				jdbcTemplate.update(
						SqlQuery.SQL_INSERT_TUTORED_STUDENT,
					    new Object[] { studentID,tutorID,courseID});
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void likeTutor(TutorLikeEntry tutorLikeEntry) { 
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(
					SqlQuery.SQL_ADD_LIKE_TO_TUTOR,
				    new Object[] { tutorLikeEntry.getUserId(),tutorLikeEntry.getTutorId() });
	}

	@Override
	public int getNumLikesForTutor(String tutorId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = SqlQuery.SQL_COUNT_NUM_LIKES_FOR_TUTOR;  
		
		return  jdbcTemplate.queryForObject(sql, new Object[] { tutorId }, Integer.class);
	}

	@Override
	public void updateTutorProfile(Tutor tutor) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				SqlQuery.SQL_UPDATE_TUTOR_PROFILE,
			    new Object[] { tutor.getAbout(),tutor.getCommunicationStr(),tutor.getId() });
	}

	@Override
	public boolean isRequestPending(TutorRequest tutorRequest) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = SqlQuery.SQL_SELECT_TO_FIND_REQUEST_PENDING;  
		
		int pendingRequestsCount = jdbcTemplate.queryForObject(sql, new Object[] { tutorRequest.getTutorId(),
														tutorRequest.getStudentId(),
														tutorRequest.getCourseId() }, 
														Integer.class);
		
		return (pendingRequestsCount>0);
	}
}

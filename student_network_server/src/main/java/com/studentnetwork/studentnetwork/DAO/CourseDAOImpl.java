package com.studentnetwork.studentnetwork.DAO;

import java.util.List;
import javax.sql.DataSource;
import com.studentnetwork.studentnetwork.JDBC.CourseAboutRowMapper;
import com.studentnetwork.studentnetwork.JDBC.CourseMaterialRowMapper;
import com.studentnetwork.studentnetwork.JDBC.CourseRowMapper;
import com.studentnetwork.studentnetwork.constants.AppConstants;
import com.studentnetwork.studentnetwork.model.Course;
import com.studentnetwork.studentnetwork.model.CourseAbout;
import com.studentnetwork.studentnetwork.model.CourseMaterial;
import com.studentnetwork.studentnetwork.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDAOImpl implements CourseDAO
{
	@Autowired
	DataSource dataSource;
	
	@Override
	public List<Course> getAllCoursesOnFaculity(String faculityID)
	{
		try{
			List<Course> courses = null;
			String sql = AppConstants.SqlQuery.SQL_SELECT_COURSES_BY_FACULITY + faculityID;
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			courses = jdbcTemplate.query(sql, new CourseRowMapper());
			return courses;
		 }catch(Exception ex){
			  return null;
		}
	}

	@Override
	public CourseAbout getCourseAbout(String courseId) {
		try{
			CourseAbout courseAbout = null;
			String sql = AppConstants.SqlQuery.SQL_SELECT_COURSE_ABOUT;
			System.out.println("Executing query: "+sql);
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			List<CourseAbout> coursesList = jdbcTemplate.query(sql,
														       new CourseAboutRowMapper());

			for(CourseAbout currCourse : coursesList){
				if(currCourse.getCourseId().equals(courseId)){
					courseAbout = currCourse;
				}
			}
			System.out.println("Returning course about: "+courseAbout);
			return courseAbout;
		 }catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CourseMaterial> getCourseMaterial(String courseId)
	{
		
		List<CourseMaterial> materialToReturn = null;

		try{
		String sql = AppConstants.SqlQuery.SQL_SELECT_COURSE_MATERIAL + courseId +";";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		materialToReturn = jdbcTemplate.query(sql, new CourseMaterialRowMapper());
		return materialToReturn;
		
		 }catch(Exception ex){
			  return null;
		}
	}

	@Override
	public Status addCourseMaterial(CourseMaterial courseMaterial) {
		Status status = new Status();
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(
				AppConstants.SqlQuery.SQL_INSERT_COURSE_MATERIAL,
			    new Object[] { courseMaterial.getTitle(), courseMaterial.getImageURL(),
			    		courseMaterial.getCourseId(), courseMaterial.getExternalURL()});
			status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
		}catch(Exception ex){
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
			ex.printStackTrace();
		}
		
		return status;
	}

	@Override
	public void addCoursesForTutor(List<Course> courses,int tutorId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		System.out.println("######################### adding "+tutorId);
		
		System.out.println("QUERY = "+AppConstants.SqlQuery.SQL_INSERT_TUTORED_COURSE);
		
		System.out.println("Size of courses = "+courses.size());

		for(int i=0;i<courses.size();i++){
			System.out.println("Adding course = "+courses.get(i).getName());
			Course course = courses.get(i);
			jdbcTemplate.update(
				AppConstants.SqlQuery.SQL_INSERT_TUTORED_COURSE,
			    new Object[] { course.getId(),tutorId  });
		}
	}

	@Override
	public Course getCourseByID(String id) {
		List<Course> courseList = null;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		courseList = jdbcTemplate.query(AppConstants.SqlQuery.SQL_GET_COURSE_BY_ID+id+"'",
										new CourseRowMapper());
		  
		try
		{
			return courseList.get(0);
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public Course addCourse(Course course) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(
					AppConstants.SqlQuery.SQL_INSERT_COURSE,
					new Object[] { course.getName(),course.getFaculityID()});
			System.err.println("CourseDAO added 1");
			List<Course> courseList = null;
			courseList = jdbcTemplate.query(AppConstants.SqlQuery.SQL_GET_COURSE_BY_NAME+
							course.getName()+"'",
					new CourseRowMapper());
			return  courseList.get(0);
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

}

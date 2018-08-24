package com.studentnetwork.studentnetwork.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.studentnetwork.studentnetwork.DAO.CourseDAO;
import com.studentnetwork.studentnetwork.model.Course;
import com.studentnetwork.studentnetwork.model.CourseAbout;
import com.studentnetwork.studentnetwork.model.CourseMaterial;
import com.studentnetwork.studentnetwork.model.Status;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService
{
	@Autowired
	CourseDAO courseDAO;

	@Override
	public List<Course> getAllCoursesOnFaculity(String faculityID)
	{
		return courseDAO.getAllCoursesOnFaculity(faculityID);
	}

	@Override
	public CourseAbout getCourseAbout(String courseId) {
		return courseDAO.getCourseAbout(courseId); 
	}

	@Override
	public List<CourseMaterial> getCourseMaterial(String courseId) {
		return courseDAO.getCourseMaterial(courseId);
	}

	@Override
	public Status addCourseMaterial(CourseMaterial courseMaterial) {
		return courseDAO.addCourseMaterial(courseMaterial);
	}

	@Override
	public void addCoursesForTutor(List<Course> courses,int tutorId) {
		courseDAO.addCoursesForTutor(courses,tutorId);
	}

	@Override
	public Course getCourseByID(String id) {
		return courseDAO.getCourseByID(id);
	}

}

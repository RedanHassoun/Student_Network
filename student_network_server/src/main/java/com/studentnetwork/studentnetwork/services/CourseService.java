package com.studentnetwork.studentnetwork.services;

import java.util.List;

import com.studentnetwork.studentnetwork.model.Course;
import com.studentnetwork.studentnetwork.model.CourseAbout;
import com.studentnetwork.studentnetwork.model.CourseMaterial;
import com.studentnetwork.studentnetwork.model.Status;

public interface CourseService
{
	List<Course> getAllCoursesOnFaculity(String faculityID);
	CourseAbout getCourseAbout(String courseId);
	List<CourseMaterial> getCourseMaterial(String courseId);
	Status addCourseMaterial(CourseMaterial courseMaterial);
	void addCoursesForTutor(List<Course> courses,int tutorId);
	Course getCourseByID(String id);
}

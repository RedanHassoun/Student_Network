package com.studentnetwork.studentnetwork.controllers;

import com.studentnetwork.studentnetwork.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.studentnetwork.studentnetwork.model.CourseAbout;
import com.studentnetwork.studentnetwork.model.CourseMaterial;
import com.studentnetwork.studentnetwork.model.CourseMaterialList;
import com.studentnetwork.studentnetwork.model.CourseMessage;
import com.studentnetwork.studentnetwork.model.Status;

@RestController
public class CourseController 
{
	private CourseService courseService;

	@RequestMapping(value="/getAllCoursesOnFaculity/{faculityID}", method=RequestMethod.GET)
	public @ResponseBody CourseMessage getAllCoursesOnFaculity(@PathVariable String faculityID)
	{
		CourseMessage courseMessage = new CourseMessage();
		courseMessage.setCourses(getCourseService().getAllCoursesOnFaculity(faculityID));
		return courseMessage;
	}
	
	@RequestMapping(value="/getCourseAbout/{courseId}", method=RequestMethod.GET)
	public @ResponseBody CourseAbout getCourseAbout(@PathVariable String courseId)
	{
		System.out.println("Getting course about. course-id="+courseId);
		return getCourseService().getCourseAbout(courseId) ;
	}
	
	
	@RequestMapping(value="/getCourseMaterial/{courseId}", method=RequestMethod.GET)
	public @ResponseBody CourseMaterialList getCourseMaterial(@PathVariable String courseId)
	{
		CourseMaterialList listToReturn = new CourseMaterialList();
		listToReturn.setMaterialList(getCourseService().getCourseMaterial(courseId));
		return listToReturn; 
	}
	
	@RequestMapping(value="/addCourseMaterial", method=RequestMethod.POST)
	public @ResponseBody Status addCourseMaterial(@RequestBody CourseMaterial courseMaterial)
	{
		System.out.println("########### RECIEVED : "+courseMaterial);
		System.out.println("########### "+ courseMaterial.getTitle());
		return getCourseService().addCourseMaterial(courseMaterial);
	}

	public CourseService getCourseService() {
		return courseService;
	}

	@Autowired
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
}

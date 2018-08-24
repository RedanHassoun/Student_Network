package com.studentnetwork.studentnetwork.model;

public class CourseAbout
{
	private String id;
	private double points;
	private String description;
	private String teacherName;
	private String courseId;
	private String courseName;
	private String faculityName;
	
	public String getFaculityName() {
		return faculityName;
	}
	public void setFaculityName(String faculityName) {
		this.faculityName = faculityName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}

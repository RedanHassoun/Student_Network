package com.mohred.studentnetwork.model;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

import java.sql.Timestamp;

/**
 * Created by Redan on 2/3/2017.
 */
public class TutorRequest implements ConnectionObject,Comparable
{
    private int tutorId;
    private int studentId;
    private int courseId;
    private int status;
    private Timestamp time;
    private String studentName;
    private String userImageURL;
    private String courseName;



    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        TutorRequest anotherItem = (TutorRequest)o;

        if(anotherItem.getTime().after(this.getTime()))
            return 1;
        if(anotherItem.getTime().before(this.getTime()))
            return -1;
        return 0;
    }
}

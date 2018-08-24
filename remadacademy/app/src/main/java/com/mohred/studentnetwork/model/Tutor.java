package com.mohred.studentnetwork.model;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

import java.util.List;

/**
 * Created by Redan on 2/4/2017.
 */

public class Tutor implements ConnectionObject
{
    private String id;
    private String communicationStr;
    private String about;
    private List<Course> courses;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCommunicationStr() {
        return communicationStr;
    }
    public void setCommunicationStr(String communicationStr) {
        this.communicationStr = communicationStr;
    }
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }
    public List<Course> getCourses() {
        return courses;
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

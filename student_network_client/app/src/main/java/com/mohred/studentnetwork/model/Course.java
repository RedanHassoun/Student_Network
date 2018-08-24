package com.mohred.studentnetwork.model;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

/**
 * Created by Redan on 1/7/2017.
 */

public class Course implements ConnectionObject
{
    private String id;
    private String name;
    private String faculityID;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFaculityID() {
        return faculityID;
    }
    public void setFaculityID(String faculityID) {
        this.faculityID = faculityID;
    }
}

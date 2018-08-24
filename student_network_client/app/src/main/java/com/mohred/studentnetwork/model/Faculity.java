package com.mohred.studentnetwork.model;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

/**
 * Created by Redan on 1/6/2017.
 */

public class Faculity implements ConnectionObject
{
    private String id;
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
    public String getOrgID() {
        return orgID;
    }
    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }
    private String name;
    private String orgID;
}

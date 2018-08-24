package com.mohred.studentnetwork.model;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

import java.util.List;

/**
 * Created by Redan on 1/6/2017.
 */

public class FaculityMessage implements ConnectionObject
{
    private List<Faculity> facList;

    public List<Faculity> getFacList() {
        return facList;
    }

    public void setFacList(List<Faculity> facList) {
        this.facList = facList;
    }
}

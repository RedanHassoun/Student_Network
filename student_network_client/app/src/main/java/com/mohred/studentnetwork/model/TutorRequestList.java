package com.mohred.studentnetwork.model;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

import java.util.List;

/**
 * Created by Redan on 2/3/2017.
 */
public class TutorRequestList implements ConnectionObject
{
    private List<TutorRequest> requestList;

    public List<TutorRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<TutorRequest> requestList) {
        this.requestList = requestList;
    }

}

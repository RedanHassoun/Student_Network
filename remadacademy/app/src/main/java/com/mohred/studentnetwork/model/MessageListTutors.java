package com.mohred.studentnetwork.model;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

import java.util.List;

/**
 * Created by Redan on 1/21/2017.
 */
public class MessageListTutors implements ConnectionObject
{
    private List<UserMessage> listTutors;

    public List<UserMessage> getListTutors() {
        return listTutors;
    }

    public void setListTutors(List<UserMessage> listTutors) {
        this.listTutors = listTutors;
    }

}
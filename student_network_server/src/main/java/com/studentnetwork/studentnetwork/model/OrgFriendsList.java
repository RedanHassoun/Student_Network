package com.studentnetwork.studentnetwork.model;

import java.util.List;

public class OrgFriendsList
{
	private List<UserMessage> OrgFriendsList;
    private String status;
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserMessage> getOrgFriendsList() {
        return OrgFriendsList;
    }

    public void setOrgFriendsList(List<UserMessage> orgFriendsList) {
        OrgFriendsList = orgFriendsList;
    }
}

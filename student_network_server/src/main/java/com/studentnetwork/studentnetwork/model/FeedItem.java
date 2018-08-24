package com.studentnetwork.studentnetwork.model;

import java.sql.Timestamp;

public class FeedItem
{
	private String id;
    private String userId;
    private String postImageURL;
    private String username;
    private String status;
    private String profileImageURL;
    private Timestamp timeStamp;
    private String externamURL;
    private String ogranizationId;
    private String email;
    public String getOgranizationId() {
        return ogranizationId;
    }
    public void setOgranizationId(String ogranizationId) {
        this.ogranizationId = ogranizationId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPostImageURL() {
        return postImageURL;
    }
    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getProfileImageURL() {
        return profileImageURL;
    }
    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }
    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getExternamURL() {
        return externamURL;
    }
    public void setExternamURL(String externamURL) {
        this.externamURL = externamURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

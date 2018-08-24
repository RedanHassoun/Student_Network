package com.studentnetwork.studentnetwork.model;

import java.sql.Timestamp;

public class UserMessage {
	private int registerType;
	private String userId;
    private String ogranizationId;
    private String organizationName;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String loginMethod;
    private String imageURL;
    private String about;
    private String domainId;
    private String facilityId;
    private String facilityName;
    private int numLikes;
    private String communicationStr;
    private String aboutTutor;
    private boolean isTutor=false;
    private Timestamp datedStartedToBeTutored;
    private String tutoredCourseName;
    private String firebaseToken;

    

    public String getFirebaseToken() {
		return firebaseToken;
	}

	public void setFirebaseToken(String firebaseToken) {
		this.firebaseToken = firebaseToken;
	}

	public Timestamp getDatedStartedToBeTutored() {
        return datedStartedToBeTutored;
    }

    public void setDatedStartedToBeTutored(Timestamp datedStartedToBeTutored) {
        this.datedStartedToBeTutored = datedStartedToBeTutored;
    }



    public String getTutoredCourseName() {
        return tutoredCourseName;
    }

    public void setTutoredCourseName(String tutoredCourseName) {
        this.tutoredCourseName = tutoredCourseName;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public String getCommunicationStr() {
        return communicationStr;
    }

    public void setCommunicationStr(String communicationStr) {
        this.communicationStr = communicationStr;
    }

    public String getAboutTutor() {
        return aboutTutor;
    }

    public void setAboutTutor(String aboutTutor) {
        this.aboutTutor = aboutTutor;
    }



    public boolean isTutor() {
        return isTutor;
    }

    public void setTutor(boolean isTutor) {
        this.isTutor = isTutor;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOgranizationId() {
        return ogranizationId;
    }

    public void setOgranizationId(String id) {
        this.ogranizationId = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(String loginMethod) {
        this.loginMethod = loginMethod;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    //don't use in server side
    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

	public int getRegisterType() {
		return registerType;
	}

	public void setRegisterType(int registerType) {
		this.registerType = registerType;
	}
}

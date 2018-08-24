package com.mohred.studentnetwork.model;

/**
 * Created by Redan on 12/3/2016.
 */
public class User
{
    private String id;
    private String dateOfBirth;
    private String fullName;
    private String imageURL;
    private String loginPlatformToken;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String loginPlatformId;
    private boolean authenticated;
    private String organizationId;
    private String loginMethod;
    private String organizationName;
    private String about;
    private String faculityID;
    private boolean isTutor = false;
    private String facilityName;
    private String firebaseToken;
    private boolean isTokenChanged = true;

    public String getFaculityID() {
        return faculityID;
    }

    public void setFaculityID(String faculityID) {
    this.faculityID = faculityID;
}

    public boolean isTutor() {
        return isTutor;
    }

    public void setTutor(boolean tutor) {
        isTutor = tutor;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public boolean isTokenChanged() {
        return isTokenChanged;
    }

    public void setTokenChanged(boolean tokenChanged) {
        isTokenChanged = tokenChanged;
    }

    public static class Builder
    {
        private String id;
        private String loginPlatformId;
        private String loginPlatformToken;
        private String dateOfBirth;
        private String phoneNumber;
        private String fullName;
        private String imageURL ="";
        private String username="";
        private String email="";
        private boolean authenticated = false;
        private String password;
        private String organizationId;
        private String loginMethod;
        private String organizationName;
        private String about;
        private String faculityID;
        private boolean isTutor;
        private String facilityName;

        public Builder() {}
        public Builder setLoginPlatformId(String loginPlatformId)
        {this.loginPlatformId = loginPlatformId; return this;}
        public Builder setImageURL(String val)
        { imageURL = val; return this; }
        public Builder setFacilityName(String val)
        { facilityName = val; return this; }
        public Builder setId(String id)
        { this.id = id; return this;}
        public Builder setFacuiltyID(String facuiltyID)
        { this.faculityID = facuiltyID ; return this; }
        public Builder setLoginMethod(String loginMethod)
        { this.loginMethod = loginMethod ; return  this;}
        public Builder setOrganizationName(String organizationName)
        { this.organizationName = organizationName; return this; }
        public Builder setPhoneNumber(String phoneNumber)
        { this.phoneNumber = phoneNumber; return this; }
        public Builder setAbout(String about)
        { this.about = about ; return this;}
        public Builder setIsTutor(boolean isTutor)
        { this.isTutor = isTutor; return this; }
        public Builder setUsername(String val)
        { username = val; return this; }
        public Builder setOrganizationId(String val)
        { organizationId = val; return this; }
        public Builder setFullName(String val)
        { fullName = val; return this; }
        public Builder setDateOfBirth(String val)
        { dateOfBirth = val; return this; }
        public Builder setEmail(String val)
        { email = val; return this; }
        public Builder setAuthenticated(boolean val)
        { authenticated = val; return this; }
        public Builder setPassword(String password) {this.password = password; return this;}
        public Builder setLoginPlatformToken(String loginPlatformToken)
        {this.loginPlatformToken = loginPlatformToken; return this;}
        public User build() {
            return new User(this);
        }


    }

    private User(Builder builder)
    {
        setLoginPlatformId(builder.loginPlatformId);
        setEmail(builder.email);
        setImageURL(builder.imageURL);
        setUsername(builder.username);
        setLoginPlatformToken(builder.loginPlatformToken);
        setAuthenticated(builder.authenticated);
        setPassword(builder.password);
        setFullName(builder.fullName);
        setDateOfBirth(builder.dateOfBirth);
        setPhoneNumber(builder.phoneNumber);
        setOrganizationId(builder.organizationId);
        setOrganizationName(builder.organizationName);
        setLoginMethod(builder.loginMethod);
        setId(builder.id);
        setAbout(builder.about);
        setFaculityID(builder.faculityID);
        setFacilityName(builder.facilityName);
        setTutor(builder.isTutor);

    }
    public String getLoginMethod() {
        return loginMethod;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAbout() {return about;}
    public void setAbout(String about) { this.about = about;}
    public void setLoginMethod(String loginMethod) {
        this.loginMethod = loginMethod;
    }
    public boolean isAuthenticated()
    {
        return authenticated;
    }
    public void setAuthenticated(boolean authenticated)
    {
        this.authenticated = authenticated;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLoginPlatformId() {
        return loginPlatformId;
    }
    public void setLoginPlatformId(String loginPlatformId) {this.loginPlatformId = loginPlatformId;}
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public String getLoginPlatformToken() {
        return loginPlatformToken;
    }
    public void setLoginPlatformToken(String loginPlatformToken) {this.loginPlatformToken = loginPlatformToken;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getOrganizationId() {
        return organizationId;
    }
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public UserMessage getUserMessage()
    {
        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(this.getId());
        userMessage.setEmail(this.getEmail());
        userMessage.setUsername(this.getUsername());
        userMessage.setFullName(this.getFullName());
        userMessage.setPassword(this.getPassword());
        userMessage.setOgranizationId(this.getOrganizationId());
        userMessage.setLoginMethod(this.getLoginMethod());
        userMessage.setImageURL(this.getImageURL());
        userMessage.setDomainId(this.getLoginPlatformId());
        userMessage.setFacilityId(this.getFaculityID());
        userMessage.setOrganizationName(this.getOrganizationName());
        userMessage.setTutor(this.isTutor());
        userMessage.setFirebaseToken(this.firebaseToken);
        userMessage.setFacilityName(this.facilityName);
        userMessage.setAbout(this.about);
        // TODO - make the rest of fields
        return userMessage;
    }

    @Override
    public String toString()
    {
        return "[ username="+username+
                ", password="+password+
                ",isTutor = "+isTutor()+
                ",orgid = "+this.organizationId+
                " ]";
    }
}

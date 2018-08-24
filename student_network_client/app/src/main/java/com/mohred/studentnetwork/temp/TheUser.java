package com.mohred.studentnetwork.temp;

/**
 * Created by Redan on 12/10/2016.
 */

public class TheUser {
    private String username;
    private String password;

    public TheUser()
    {

    }

    public TheUser(String username,String password)
    {
        this.username = username;
        this.password = password;
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
}

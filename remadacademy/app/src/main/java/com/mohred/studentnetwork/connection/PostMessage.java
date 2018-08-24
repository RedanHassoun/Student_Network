package com.mohred.studentnetwork.connection;

import android.util.Log;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

import java.util.ArrayList;

import static com.mohred.studentnetwork.common.AppConstants.URLs.BASE_URL;

/**
 * Created by Redan on 12/16/2016.
 */

public abstract class PostMessage {
    protected String messageURL;
    public String  buildMessage(ArrayList<String> paramsList)
    {
        String url = BASE_URL;
        for(String currentToken : paramsList)
        {
            url += "/" + currentToken;
        }
        Log.d("CONNECTION_PROBLEM","post url: "+url);
        return url;
    }
    public abstract ConnectionObject sendMessage(ConnectionObject toPost);
}

package com.mohred.studentnetwork.connection;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

/**
 * Created by Redan on 1/7/2017.
 */
public class DataPostLoaderTask extends AsyncTaskLoader<ConnectionObject>
{
    private ConnectionObject mData;
    private PostMessage message;
    private ConnectionObject toPost;
    private static final String TAG = "adapter_tutor_req";

    public DataPostLoaderTask(Context context, PostMessage message,ConnectionObject toPost)
    {
        super(context);
        this.message = message;
        this.toPost = toPost;
    }

    @Override
    protected void onStartLoading()
    {
        Log.d(TAG,"starting loading");

        forceLoad();
    }

    @Override
    public ConnectionObject loadInBackground()
    {
        try {
            Log.d(TAG,"sending the message");
            Log.d("CONNECTION_PROBLEM","Sending post message");
            ConnectionObject toReturn = message.sendMessage(toPost);
            return toReturn;
        }catch (RuntimeException ex){
            ex.printStackTrace();
            return null;
        }

    }


    @Override
    public void deliverResult(ConnectionObject data)
    {
        Log.d(TAG,"got the result");
        // We’ll save the data for later retrieval
        mData = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }
}
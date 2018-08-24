package com.mohred.studentnetwork.connection;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

/**
 * Created by Redan on 1/6/2017.
 */
public  class DataGetLoaderTask extends AsyncTaskLoader<ConnectionObject>
{
    private ConnectionObject mData;
    private GetMessage message;
    private static final String TAG = "get_data_loader";

    public DataGetLoaderTask(Context context,GetMessage message)
    {
        super(context);
        this.message = message;
    }

    @Override
    protected void onStartLoading()
    {
        Log.d(TAG,"loading data :"+message.messageURL);
        Log.d("CONNECTION_PROBLEM","loading data :"+message);
        forceLoad();
    }

    @Override
    public ConnectionObject loadInBackground()
    {
        ConnectionObject toReturn = null;
        try {
            Log.d("CONNECTION_PROBLEM","Sending get message");
            toReturn = message.sendMessage();
            Log.d("CONNECTION_PROBLEM","get message sent");
        }
        catch (Exception ex)
        {ex.printStackTrace();}
        return toReturn;
    }


    @Override
    public void deliverResult(ConnectionObject data)
    {
        // We’ll save the data for later retrieval
        mData = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }
}

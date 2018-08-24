package com.mohred.studentnetwork.temp;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.connection.DataGetLoaderTask;
import com.mohred.studentnetwork.connection.GetGlobalPostsMessage;
import com.mohred.studentnetwork.connection.GetOrganizationsMessage;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.FeedItem;
import com.mohred.studentnetwork.model.FeedList;
import com.mohred.studentnetwork.model.Organization;
import com.mohred.studentnetwork.model.OrganizationsMessage;

/**
 * Created by Redan on 1/6/2017.
 */

public class TempLoaderActivity extends AppCompatActivity
{
    private static final String TAG = "temp_loader";
    private static final int LOADER_UNIQE_ID = 0;
    private static final int LOADER_UNIQE_ID_2 = 1;
    private LoaderManager loaderManager;
    private TextView textOrgs;
    private TextView textPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_activity_2);
        initViews();
        loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(LOADER_UNIQE_ID, null, mLoaderCallbacks);
        loaderManager.restartLoader(LOADER_UNIQE_ID_2, null, mLoaderCallbacksPosts);
    }

    private void initViews()
    {
        textOrgs = (TextView) findViewById(R.id.text_orgs);
        textPosts = (TextView) findViewById(R.id.text_login);

    }

    private LoaderManager.LoaderCallbacks<ConnectionObject>
            mLoaderCallbacksPosts =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                    return new DataGetLoaderTask(TempLoaderActivity.this,new GetGlobalPostsMessage("",0));
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        Log.d(TAG,"NULL");
                        return;
                    }
                    if(data instanceof FeedList){
                        FeedList posts = (FeedList) data;
                        FeedItem item = posts.getFeedList().get(0);
                        textPosts.setText("POST = "+item.getStatus()+", "+item.getUsername());
                    }
                }
                @Override
                public void onLoaderReset(Loader<ConnectionObject> loader)
                {
                    // Loader reset, throw away our data,
                    // unregister any listeners, etc.

                    // Of course, unless you use destroyLoader(),
                    // this is called when everything is already dying
                    // so a completely empty onLoaderReset() is
                    // totally acceptable
                }
            };


    private LoaderManager.LoaderCallbacks<ConnectionObject>
            mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                    return new DataGetLoaderTask(TempLoaderActivity.this,new GetOrganizationsMessage());
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        Log.d(TAG,"NULL");
                        return;
                    }
                    if(data instanceof OrganizationsMessage){
                        OrganizationsMessage organizationsMessage = (OrganizationsMessage) data;
                        Organization org = organizationsMessage.getOrgs().get(0);
                        textOrgs.setText("ORG = "+org.getName()+", "+org.getType());
                    }
                }
                @Override
                public void onLoaderReset(Loader<ConnectionObject> loader)
                {
                    // Loader reset, throw away our data,
                    // unregister any listeners, etc.

                    // Of course, unless you use destroyLoader(),
                    // this is called when everything is already dying
                    // so a completely empty onLoaderReset() is
                    // totally acceptable
                }
            };
}

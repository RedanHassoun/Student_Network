package com.mohred.studentnetwork.page_main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import com.android.volley.toolbox.ImageLoader;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.app.AppController;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.DataPostLoaderTask;
import com.mohred.studentnetwork.connection.DeletePostMessage;
import com.mohred.studentnetwork.custom.FeedImageView;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.FeedItem;
import com.mohred.studentnetwork.model.Status;
import com.mohred.studentnetwork.page_fullscreen_image.ActivityFullScreenImage;
import com.mohred.studentnetwork.page_profile.FragmentShowProfile;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.mohred.studentnetwork.common.AppConstants.FragmentNames.FRAGMENT_OTHER_USER_PROFILE;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_IMAGE_URL;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_PROGILE_USER_MAIL;





/**
 * Created by Redan on 12/23/2016.
 */

public class FeedListAdapter extends BaseAdapter
{
    private static final String TAG = "feed_adapter";
    private Activity activity;
    private Fragment fragment;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    DataManager dataManager = DataManager.getInstance();
    private static final int DELETE_POST_LOADER_ID = 34;
    private LoaderManager loaderManager;
    private FeedItem feedItem;
    public FeedListAdapter(Activity activity,Fragment fragmen, List<FeedItem> feedItems)
    {
        this.activity = activity;
        this.feedItems = feedItems;
        this.fragment=fragmen;
    }

    @Override
    public int getCount()
    {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location)
    {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_feed, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        final FeedItem item = feedItems.get(position);

        ImageView deleteButton = (ImageView) convertView.findViewById(R.id.deleteButton);
        deleteButton.setVisibility(View.INVISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showToastMessage(activity,"Deleted..");
                feedItem=item;
                loaderManager = fragment.getLoaderManager();
                loaderManager.restartLoader(DELETE_POST_LOADER_ID, null, loaderCallbackForCourses);

                //openProfileByMail(item.getEmail());
            }
        });
      /**  ImageView editButton = (ImageView) convertView.findViewById(R.id.editButton);
        editButton.setVisibility(View.INVISIBLE);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //openProfileByMail(item.getEmail());
            }
        });**/
        //view post remove button
        if(dataManager.getCurrentUser(activity).getId().toString().equals(item.getUserId().toString()))
        {
            deleteButton.setVisibility(View.VISIBLE);
           // editButton.setVisibility(View.VISIBLE);
            //set visible
        }



        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileByMail(item.getEmail());
            }
        });
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        ImageView profilePic = (ImageView) convertView
                .findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileByMail(item.getEmail());
            }
        });
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);



        name.setText(item.getUsername());

        Log.d(TAG,"current timestamp = "+item.getTimeStamp().toString());


        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(""+item.getTimeStamp().getTime()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item.getExternamURL() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getExternamURL() + "\">"
                    + item.getExternamURL() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        Log.d(TAG,"Image URL = "+item.getProfileImageURL());

        // user profile pic
        if( item.getProfileImageURL() != null && !item.getProfileImageURL().equals(""))
            Picasso.with(activity)
                .load(item.getProfileImageURL())
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.ic_perm_identity_black_24dp)
                .into(profilePic);


        // Feed image
        if (item.getPostImageURL() != null)
        {
            // TODO - migrate from Volley to picasso
            feedImageView.setImageUrl(item.getPostImageURL(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver()
                    {
                        @Override
                        public void onError()
                        {
                        }

                        @Override
                        public void onSuccess()
                        {
                        }
                    });

            final String imageURL = item.getPostImageURL();

            feedImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ActivityFullScreenImage.class);
                    Bundle b = new Bundle();
                    b.putString(ARG_IMAGE_URL, imageURL); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    activity.startActivity(intent);
                }
            });
        } else
        {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }
    private LoaderManager.LoaderCallbacks<ConnectionObject>
            loaderCallbackForCourses =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                 //   progressBar.setVisibility(View.VISIBLE);
                    return new DataPostLoaderTask(activity,new DeletePostMessage(), GetFeedItem());

                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                  //  progressBar.setVisibility(View.GONE);
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        AppUtils.showToastMessage(activity, "Error connecting to server");
                        return;
                    }

                    try {
                       // FeedItem feeditem = (FeedItem)data;
                        AppUtils.showToastMessage(activity, "Deleted Successfuly ,"+((Status)data).getStatus());


                    }catch (Exception ex){
                        ex.printStackTrace();

                    }
                }
                @Override
                public void onLoaderReset(Loader<ConnectionObject> loader)
                {
                }
            };
    private FeedItem GetFeedItem()
    {
        return this.feedItem;
    }
    private void openProfileByMail(String mail)
    {
        FragmentShowProfile fragment = FragmentShowProfile.newInstance();
        Bundle args = new Bundle();
        args.putString(ARG_PROGILE_USER_MAIL,mail);
        fragment.setArguments(args);
        ((ActivityBase)activity).replaceFragment(fragment,FRAGMENT_OTHER_USER_PROFILE,args,true);
    }


}

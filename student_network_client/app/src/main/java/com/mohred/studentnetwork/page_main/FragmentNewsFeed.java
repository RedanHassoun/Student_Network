package com.mohred.studentnetwork.page_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.GetGlobalPostsMessage;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.interfaces.HTTPObserver;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.FeedItem;
import com.mohred.studentnetwork.model.FeedList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Redan on 12/23/2016.
 */
public class FragmentNewsFeed extends Fragment implements HTTPObserver,View.OnClickListener
{
    private static final String TAG = "fragment_news_feed";
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private TextView textEmptyFeed;
    private FloatingActionButton buttonAction;
    private ProgressBar progressBar;
    private View view;

    public static final FragmentNewsFeed newInstance()
    {
        FragmentNewsFeed fragment = new FragmentNewsFeed();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news_feed, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        buttonAction = (FloatingActionButton) view.findViewById(R.id.button_add_post);
        textEmptyFeed = (TextView) view.findViewById(R.id.feed_empty);

        buttonAction.setOnClickListener(this);

        initFeed();

        return view;
    }

    public void initFeed()
    {
        listView = (ListView) view.findViewById(R.id.list);

        feedItems = new ArrayList<>();

        listAdapter = new FeedListAdapter(getActivity(),this, feedItems);
        listView.setAdapter(listAdapter);

        downloadFeed();
    }

    private void downloadFeed()
    {
        progressBar.setVisibility(View.VISIBLE);
        String orgId = DataManager.getInstance().getCurrentUser(getContext()).getOrganizationId();
        if(orgId == null){
            throw new RuntimeException("Got a null organization id from the local storage");
        }
        GetGlobalPostsMessage msg = new GetGlobalPostsMessage(orgId,0);
        DataManager.getInstance().getHttpExecutionPool().sentGETMessage(msg,this);
    }

    @Override
    public void update(final ConnectionObject connectionObject)
    {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {

                progressBar.setVisibility(View.GONE);
                if(connectionObject == null){
                    AppUtils.showToastMessage(getContext(),getString(R.string.unable_to_connect_to_server));
                    return;
                }

                if(connectionObject instanceof FeedList){
                    FeedList responseFeed = (FeedList) connectionObject;
                    List<FeedItem> feed = responseFeed.getFeedList();
                    if(feed.size() == 0){
                        textEmptyFeed.setVisibility(View.VISIBLE);
                    }else {
                        textEmptyFeed.setVisibility(View.GONE);
                        Collections.sort(feed);

                        for(FeedItem item : feed){
                            feedItems.add(item);

                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.button_add_post:
                DialogFragmentNewPost newFragment = DialogFragmentNewPost.newInstance(this);
                newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
                break;
        }
    }
}

package com.studentnetwork.studentnetwork.services;

import com.studentnetwork.studentnetwork.model.FeedItem;
import com.studentnetwork.studentnetwork.model.FeedList;

public interface NewsFeedService
{
	FeedList getGlobalFeedFromIndex(String orgId,int index);
	void addNewPost(FeedItem feedItem);
	void deletePost(FeedItem feedItem);
	
	
}

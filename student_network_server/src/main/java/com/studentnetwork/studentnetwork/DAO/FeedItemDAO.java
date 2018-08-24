package com.studentnetwork.studentnetwork.DAO;

import com.studentnetwork.studentnetwork.model.FeedItem;
import com.studentnetwork.studentnetwork.model.FeedList;

public interface FeedItemDAO
{
	FeedList getGlobalFeedFromIndex(String orgId,int index);
	void addNewPost(FeedItem feedItem);
	void deletePost(FeedItem feedItem);
	
	
}

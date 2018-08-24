package com.studentnetwork.studentnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.studentnetwork.studentnetwork.DAO.FeedItemDAO;
import com.studentnetwork.studentnetwork.DAO.UserDao;
import com.studentnetwork.studentnetwork.model.FeedItem;
import com.studentnetwork.studentnetwork.model.FeedList;
import com.studentnetwork.studentnetwork.model.UserMessage;
import org.springframework.stereotype.Service;

@Service
public class NewsFeedServiceImpl implements NewsFeedService
{
	@Autowired
	FeedItemDAO feedItemDao;
	
	@Autowired
	UserDao userdao;

	
	@Override
	public FeedList getGlobalFeedFromIndex(String orgId,int index)
	{
		FeedList feedList = feedItemDao.getGlobalFeedFromIndex(orgId,index);
		/*
		 * Fill missing data for the feed items 
		 */
		for(int i=0;i<feedList.getFeedList().size();i++)
		{
			UserMessage user = userdao.getUserById(feedList
														.getFeedList()
														.get(i)
														.getUserId());
			
			System.out.println("####%%%% IMAGE URL = "+user.getImageURL());
			
			feedList.getFeedList().get(i).setProfileImageURL(user.getImageURL());
			feedList.getFeedList().get(i).setUsername(user.getUsername());
			feedList.getFeedList().get(i).setEmail(user.getEmail());
		}
		
		return feedList;
	}
	@Override
	public void deletePost(FeedItem feedItem)
	{
		feedItemDao.deletePost(feedItem);
	}
	@Override
	public void addNewPost(FeedItem feedItem)
	{
		feedItemDao.addNewPost(feedItem);
	}

}

package com.studentnetwork.studentnetwork.controllers;

import com.studentnetwork.studentnetwork.constants.AppConstants;
import com.studentnetwork.studentnetwork.services.NewsFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.studentnetwork.studentnetwork.model.FeedItem;
import com.studentnetwork.studentnetwork.model.FeedList;
import com.studentnetwork.studentnetwork.model.Status;

@RestController
public class FeedPostsController 
{
	@Autowired
	NewsFeedService newsFeedService;


	/**
	 * This method returns a number of global posts from a specified index
	 * and indicates when there is no more posts available in the database  
	 * @param index
	 * @return
	 */
	 @RequestMapping(value="/getFeedFromIndex/{orgId}/{index}", method=RequestMethod.GET)
	 public @ResponseBody FeedList getFeedFromIndex(@PathVariable String orgId,@PathVariable int index)
	 { 
		 FeedList feedToReturn = newsFeedService.getGlobalFeedFromIndex(orgId,index); 
		 return feedToReturn;	
	 }
	 
	 
	 /**
	  * Get the user as a POST message from the client, try to register it to the database and
	  * return the appropriate message
	  * @param user
	  * @return
	  */
	@RequestMapping(value="/addPost", method=RequestMethod.POST)
	public @ResponseBody Status addPost(@RequestBody FeedItem feedItem)
	{
		System.out.println("Status = "+feedItem.getStatus());
		System.out.println("User id = "+feedItem.getUserId());
		System.out.println("Post image URL = "+feedItem.getPostImageURL());
		System.out.println("External URL = "+feedItem.getExternamURL());
		System.out.println("Org_is = "+feedItem.getOgranizationId());
		Status status = new Status();
		try{
			newsFeedService.addNewPost(feedItem);
			status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
		}catch(Exception ex){
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
		}
		 return status; 
	}
	
	
	 /**
	  *  a POST message from the client, Delete post
	  * return the appropriate message
	  * @param user
	  * @return
	  */
	@RequestMapping(value="/deletePost", method=RequestMethod.POST)
	public @ResponseBody Status deletePost(@RequestBody FeedItem feedItem)
	{
		
		Status status = new Status();
		try{
			newsFeedService.deletePost(feedItem);
			status.setStatus(AppConstants.ConnectionConstants.STATUS_OK);
		}catch(Exception ex){
			status.setStatus(AppConstants.ConnectionConstants.STATUS_NOT_OK);
		}
		 return status; 
	}
}

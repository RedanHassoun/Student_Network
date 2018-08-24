package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studentnetwork.studentnetwork.DAO.UserDao;
import com.studentnetwork.studentnetwork.model.FeedItem;
import com.studentnetwork.studentnetwork.model.UserMessage;
import com.studentnetwork.studentnetwork.services.UserService;

public class FeedItemExtractor implements ResultSetExtractor<FeedItem> 
{ 
 
	public FeedItem extractData(ResultSet resultSet) throws SQLException,
	   															DataAccessException 
	{ 
		 FeedItem feedItem = new FeedItem();
		 
		 feedItem.setId(""+resultSet.getInt(1));
		 feedItem.setUserId(""+resultSet.getInt(2));
		 feedItem.setPostImageURL(resultSet.getString(3));
		 feedItem.setStatus(resultSet.getString(4));
		 feedItem.setTimeStamp(resultSet.getTimestamp(5));
		 feedItem.setExternamURL(resultSet.getString(6));
		 
		 
		 
	  
		 return feedItem;
	}
}
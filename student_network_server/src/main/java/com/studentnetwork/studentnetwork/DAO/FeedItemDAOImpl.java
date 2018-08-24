package com.studentnetwork.studentnetwork.DAO;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.studentnetwork.studentnetwork.JDBC.FeedItemRowMapper;
import com.studentnetwork.studentnetwork.constants.AppConstants.ConnectionConstants;
import com.studentnetwork.studentnetwork.constants.AppConstants.SqlQuery;
import com.studentnetwork.studentnetwork.model.FeedItem;
import com.studentnetwork.studentnetwork.model.FeedList;
import org.springframework.stereotype.Repository;

@Repository
public class FeedItemDAOImpl implements FeedItemDAO
{
	@Autowired
	DataSource dataSource;
	
	

	@Override
	public FeedList getGlobalFeedFromIndex(String orgId,int index) 
	{
		// TODO - handle the cases when the index is at the end or the index 
		// is wrong 
		List<FeedItem> feedList = new ArrayList<FeedItem>();
		String sql = SqlQuery.SQL_SELECT_POSTS_FROM_INDEX + orgId;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		feedList = jdbcTemplate.query(sql, new FeedItemRowMapper());
		  
		try
		{
			
			/*
			 * Prepare respond
			 */
			FeedList feedToReturn= new FeedList();
			feedToReturn.setFeedList(feedList);
			return feedToReturn;
		}
		catch(Exception ex)
		{
			return null;
		}
	}



	@Override
	public void addNewPost(FeedItem feedItem) 
	{ 
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
 
		jdbcTemplate.update(
			SqlQuery.SQL_INSERT_POST,
		    new Object[] { feedItem.getUserId(), feedItem.getPostImageURL(),
		    		feedItem.getStatus(), feedItem.getExternamURL(),feedItem.getOgranizationId()});
		 
	}
	@Override
	public void deletePost(FeedItem feedItem) 
	{ 
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
 
		jdbcTemplate.update(
			SqlQuery.SQL_DELETE_POST,
		    new Object[] { feedItem.getId()});
		 
	}
	
	
}

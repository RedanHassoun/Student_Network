package com.studentnetwork.studentnetwork.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studentnetwork.studentnetwork.model.FeedItem;
import com.studentnetwork.studentnetwork.services.UserService;

public class FeedItemRowMapper implements RowMapper<FeedItem>
{ 
	 @Override
	 public FeedItem mapRow(ResultSet resultSet, int line) throws SQLException 
	 {
		 FeedItemExtractor feedItemExtractor = new FeedItemExtractor();
		 return feedItemExtractor.extractData(resultSet);
	 }
}

package com.studentnetwork.studentnetwork.model;

import java.util.List;

public class FeedList
{
	private List<FeedItem> feedList;
    private boolean isEnd;

    public List<FeedItem> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<FeedItem> feedList) {
        this.feedList = feedList;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}

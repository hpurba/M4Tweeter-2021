package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedTweetsRequest {


    private User user;
    private int limit;
    private Tweet lastTweet;
    private String authToken;

    /**
     * Empty Default constructor
     */
    public FeedTweetsRequest() { }


    /**
     * Creates an instance.
     *
     * @param user
     * @param limit the maximum number of followers to return.
     * @param lastTweet the last tweet that was returned in the previous request (null if there was no previous request or if no followers were returned in the previous request).
     * @param authToken the authToken
     */
    public FeedTweetsRequest(User user, int limit, Tweet lastTweet, String authToken) {
        this.user = user;
        this.limit = limit;
        this.lastTweet = lastTweet;
        this.authToken = authToken;
    }

    /**
     * Returns the tweet whose tweet are to be returned by this request.
     *
     * @return the followee.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the number representing the maximum number of tweet to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last tweet that was returned in the previous request or null if there was no
     * previous request or if no followers were returned in the previous request.
     *
     * @return the last Tweet.
     */
    public Tweet getLastTweet() {
        return lastTweet;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastTweet(Tweet lastTweet) {
        this.lastTweet = lastTweet;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

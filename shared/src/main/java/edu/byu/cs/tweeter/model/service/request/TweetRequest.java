package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Tweet;

public class TweetRequest {

    private Tweet tweet;
    private String authToken;

    /**
     * Empty Default constructor
     */
    public TweetRequest() { }

    public TweetRequest(Tweet tweet, String tweetText) {
        this.tweet = tweet;
        this.authToken = authToken;
    }

    public String getUsername() {
        return tweet.getAlias();
    }

    public String getTweetText() {
        return tweet.getTweetText();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
}

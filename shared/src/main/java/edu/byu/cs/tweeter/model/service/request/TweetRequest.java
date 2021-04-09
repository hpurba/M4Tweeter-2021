package edu.byu.cs.tweeter.model.service.request;

public class TweetRequest {

    private String username;
    private String tweetText;

    /**
     * Empty Default constructor
     */
    public TweetRequest() { }

    public TweetRequest(String username, String tweetText) {
        this.username = username;
        this.tweetText = tweetText;
    }

    public String getUsername() {
        return username;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }
}

package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Tweet;

public class StoryTweetsRequest {

    private Tweet tweet;
    private int limit;
    private Tweet lastTweet;


    public StoryTweetsRequest() {}


    /**
     * Creates an instance.
     *
     * @param tweet the {@link Tweet} whose tweet are to be returned.
     * @param limit the maximum number of followers to return.
     * @param tweet the last tweet that was returned in the previous request (null if
     *                     there was no previous request or if no followers were returned in the
     *                     previous request).
     */
    public StoryTweetsRequest(Tweet tweet, int limit, Tweet lastTweet) {
        this.tweet = tweet;
        this.limit = limit;
        this.lastTweet = lastTweet;
    }

    /**
     * Returns the tweet whose tweet are to be returned by this request.
     *
     * @return the followee.
     */
    public Tweet getTweet() {
        return tweet;
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

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastTweet(Tweet lastTweet) {
        this.lastTweet = lastTweet;
    }
}

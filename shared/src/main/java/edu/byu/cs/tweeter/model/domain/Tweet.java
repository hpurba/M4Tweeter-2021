package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class Tweet implements Comparable<Tweet>, Serializable {

    private User user;
//    private final String alias;
    private String tweetText;
    private long timestamp;

    public Tweet() {}

    // Constructor
    public Tweet(User user, String tweetText, long timestamp) {
        this.user = user;
        this.tweetText = tweetText;
        this.timestamp = timestamp;
    }

    public String getAlias() {
        return user.getAlias();
    }

    public String getTweetText() {
        return tweetText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweetComp = (Tweet) o;
        return user.getAlias().equals(tweetComp.getAlias()) && tweetText.equals(tweetComp.tweetText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getAlias());
    }

    @Override
    public int compareTo(Tweet tweet) {
        int sameAlias = this.getAlias().compareTo(tweet.getAlias());
        int sameTweetText = (this.getTweetText().compareTo(tweet.getTweetText()));

        if ((sameAlias == sameTweetText) && (sameAlias == 1)) {
            return 1;
        } else {
            return 0;
        }
    }
}

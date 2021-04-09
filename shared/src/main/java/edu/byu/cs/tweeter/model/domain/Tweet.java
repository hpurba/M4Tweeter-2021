package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class Tweet implements Comparable<Tweet>, Serializable {

    private final String alias;
    private final String tweetText;
    private final String userName;

    public String getUserName() {
        return userName;
    }

    // Constructor
    public Tweet(String alias, String tweetText, String userName) {
        this.alias = alias;
        this.tweetText = tweetText;
        this.userName = userName;
    }

    public String getAlias() {
        return alias;
    }

    public String getTweetText() {
        return tweetText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweetComp = (Tweet) o;
        return alias.equals(tweetComp.alias) && tweetText.equals(tweetComp.tweetText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
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

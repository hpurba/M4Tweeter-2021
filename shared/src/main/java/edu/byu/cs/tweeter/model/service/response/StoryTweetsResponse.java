package edu.byu.cs.tweeter.model.service.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.Tweet;

public class StoryTweetsResponse extends PagedResponse {

    private List<Tweet> tweets;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public StoryTweetsResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param tweets the tweets to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public StoryTweetsResponse(List<Tweet> tweets, boolean hasMorePages) {
        super(true, hasMorePages);
        this.tweets = tweets;
    }

    /**
     * Returns the tweets for the corresponding request.
     *
     * @return the tweets.
     */
    public List<Tweet> getTweets() {
        return tweets;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        StoryTweetsResponse that = (StoryTweetsResponse) param;

        return (Objects.equals(tweets, that.tweets) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tweets);
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}

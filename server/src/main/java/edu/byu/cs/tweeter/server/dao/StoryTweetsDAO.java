package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;

public class StoryTweetsDAO {

    public StoryTweetsResponse getStoryTweets(StoryTweetsRequest request) {

        // This is a List of all the dummy Tweets.
        List<Tweet> allTweets = getDummyTweetsForStory();


        // Set the request Limit to request.getLimit();
        int requestLimit = request.getLimit();
        if (requestLimit < 8) { // Ensure the request limit is at least 8.
            requestLimit = 8;
        }


        List<Tweet> responseTweets = new ArrayList<>(requestLimit);

        boolean hasMorePages = true;

        if(requestLimit > 0) {
            int tweetsIndex = getTweetsStartingIndex(request.getLastTweet(), allTweets);

            for(int limitCounter = 0; tweetsIndex < allTweets.size() && limitCounter < requestLimit; tweetsIndex++, limitCounter++) {
                responseTweets.add(allTweets.get(tweetsIndex));
            }

            hasMorePages = tweetsIndex < allTweets.size();
        }

        return new StoryTweetsResponse(responseTweets, hasMorePages);
//        return new FeedTweetsResponse(allTweets, hasMorePages);
    }

    /**
     * Returns the list of dummy tweet data. This is written as a separate method to allow
     * mocking of the tweets.
     *
     * @return the generator.
     */
    List<Tweet> getDummyTweetsForStory() {
        return Arrays.asList(tweet_S1, tweet_S2, tweet_S3, tweet_S4, tweet_S5, tweet_S6, tweet_S7, tweet_S8, tweet_S9, tweet_S10, tweet_S11, tweet_S12,
                tweet_S13, tweet_S14, tweet_S15, tweet_S16);
    }

    /**
     * Determines the index for the first tweet in the specified 'allTweets' list that should
     * be returned in the current request. This will be the index of the next tweet after the
     * specified 'lastTweet'.
     *
     * @param lastTweet the last tweet that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allTweets the generated list of tweets from which we are returning paged results.
     * @return the index of the first tweet to be returned.
     */
    private int getTweetsStartingIndex(Tweet lastTweet, List<Tweet> allTweets) {

        int tweetsIndex = 0;

        if(lastTweet != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allTweets.size(); i++) {
                if(lastTweet.equals(allTweets.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    tweetsIndex = i + 1;
                    break;
                }
            }
        }

        return tweetsIndex;
    }


    // Collection of Tweet(s). [tweet_S1 - tweet_S16]
    // These tweets are made by the @TestUser.
    // Therefore, these are tweets for @TestUser(s) story
    private final Tweet tweet_S1 = new Tweet("@TestUser", "The greatest glory in living lies not in never falling, but in rising every time we fall.", "TestUser");
    private final Tweet tweet_S2 = new Tweet("@TestUser", "The way to get started is to quit talking and begin doing.", "TestUser");
    private final Tweet tweet_S3 = new Tweet("@TestUser", "Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking.", "TestUser");
    private final Tweet tweet_S4 = new Tweet("@TestUser", "If life were predictable it would cease to be life, and be without flavor.", "TestUser");
    private final Tweet tweet_S5 = new Tweet("@TestUser", "If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough.", "TestUser");
    private final Tweet tweet_S6 = new Tweet("@TestUser", "If you set your goals ridiculously high and it's a failure, you will fail above everyone else's success.", "TestUser");
    private final Tweet tweet_S7 = new Tweet("@TestUser", "Life is what happens when you're busy making other plans.", "TestUser");
    private final Tweet tweet_S8 = new Tweet("@TestUser", "Spread love everywhere you go. Let no one ever come to you without leaving happier.", "TestUser");
    private final Tweet tweet_S9 = new Tweet("@TestUser", "When you reach the end of your rope, tie a knot in it and hang on.", "TestUser");
    private final Tweet tweet_S10 = new Tweet("@TestUser", "Always remember that you are absolutely unique. Just like everyone else.", "TestUser");
    private final Tweet tweet_S11 = new Tweet("@TestUser", "Don't judge each day by the harvest you reap but by the seeds that you plant.", "TestUser");
    private final Tweet tweet_S12 = new Tweet("@TestUser", "The future belongs to those who believe in the beauty of their dreams.", "TestUser");
    private final Tweet tweet_S13 = new Tweet("@TestUser", "Tell me and I forget. Teach me and I remember. Involve me and I learn.", "TestUser");
    private final Tweet tweet_S14 = new Tweet("@TestUser", "The best and most beautiful things in the world cannot be seen or even touched - they must be felt with the heart.", "TestUser");
    private final Tweet tweet_S15 = new Tweet("@TestUser", "Do not go where the path may lead, go instead where there is no path and leave a trail.", "TestUser");
    private final Tweet tweet_S16 = new Tweet("@TestUser", "In the end, it's not the years in your life that count. It's the life in your years.", "TestUser");



}

package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;

public class FeedTweetsDAO {

    // Collection of Tweet(s). [tweet1 - tweet20]
    private final Tweet tweet1 = new Tweet("@AllenAnderson", "I am Allen! Nice to meet you all!", "Allen Anderson");
    private final Tweet tweet2 = new Tweet("@AmyAmes", "Yooo I'm Amy.", "Amy Ames");
    private final Tweet tweet3 = new Tweet("@BobBobson", "Welcome to Bob paradise", "Bob Bobson");
    private final Tweet tweet4 = new Tweet("@BonnieBeatty", "I don't have anything to say", "Bonnie Beatty");
    private final Tweet tweet5 = new Tweet("@ChrisColston", "Motorycles are cool", "Chris Colston");
    private final Tweet tweet6 = new Tweet("@CindyCoats", "I love physics", "Cindy Coats");
    private final Tweet tweet7 = new Tweet("@DanDonaldson", "Do I look like a duck to you?", "Dan Donaldson");
    private final Tweet tweet8 = new Tweet("@DeeDempsey", "Doooo doooooooo doodly dooo", "Dee Dempsey");
    private final Tweet tweet9 = new Tweet("@ElliottEnderson", "heyoooooo", "Elliott Enderson");
    private final Tweet tweet10 = new Tweet("@ElizabethEngle", "bah blah", "Elizabeth Engle");
    private final Tweet tweet11 = new Tweet("@FrankFrandson", "frank is dope bro", "Frank Frandson");
    private final Tweet tweet12 = new Tweet("@FranFranklin", "i am rich", "Fran Franklin");
    private final Tweet tweet13 = new Tweet("@GaryGilbert", "i am poor", "Gary Gilbert");
    private final Tweet tweet14 = new Tweet("@GiovannaGiles", "i have a good life", "Giovanna Giles");
    private final Tweet tweet15 = new Tweet("@HenryHenderson", "one republic yo", "Henry Henderson");
    private final Tweet tweet16 = new Tweet("@HelenHopwell", "Have hope for the future", "Helen Hopwell");
    private final Tweet tweet17 = new Tweet("@IgorIsaacson", "You should give this guy 100% because hes a good person", "Igor Isaacson");
    private final Tweet tweet18 = new Tweet("@IsabelIsaacson", "I like to eat pizza", "Isabel Isaacson");
    private final Tweet tweet19 = new Tweet("@JustinJones", "world peace is not possible", "Justin Jones");
    private final Tweet tweet20 = new Tweet("@JillJohnson", "I am an olympian", "JillJohnson");

    public FeedTweetsResponse getFeedTweets(FeedTweetsRequest request) {

        // This is a List of all the dummy Tweets.
        List<Tweet> allTweets = getDummyTweets();

        // Set the request Limit to request.getLimit();
        int requestLimit = request.getLimit();
        if (requestLimit < 8) { // Ensure the request limit is at least 8.
            requestLimit = 8;
        }

        // This is a List of the responseTweets
        List<Tweet> responseTweets = new ArrayList<>(requestLimit);

        boolean hasMorePages = true;
        if( requestLimit > 0) {
            int tweetsIndex = getTweetsStartingIndex(request.getLastTweet(), allTweets);    // 0 if the first element.

            for(int limitCounter = 0; tweetsIndex < allTweets.size() && limitCounter < requestLimit; tweetsIndex++, limitCounter++) {
                responseTweets.add(allTweets.get(tweetsIndex));
            }

            hasMorePages = tweetsIndex < allTweets.size();
        }

        return new FeedTweetsResponse(responseTweets, hasMorePages);  // Original
//        return new FeedTweetsResponse(allTweets, hasMorePages);
    }

    /**
     * Returns the list of dummy tweet data. This is written as a separate method to allow
     * mocking of the tweets.
     *
     * @return the generator.
     */
    List<Tweet> getDummyTweets() {
        return Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8, tweet9, tweet10, tweet11, tweet12,
                tweet13, tweet14, tweet15, tweet16, tweet17, tweet18, tweet19, tweet20);
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
}

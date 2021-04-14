package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;

public class StoryTweetsServiceImplTest {
    private StoryTweetsRequest validRequest;
    private StoryTweetsRequest invalidRequest;

    private StoryTweetsResponse successResponse;
    private StoryTweetsResponse failureResponse;

    private StoryTweetsServiceImpl StoryTweetsServiceImplSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException {

        User user = new User("Hikaru", "Purba", "@hpurba", "https://tweeteruserprofileimages.s3-us-west-2.amazonaws.com/%40hpurba.png");
        Tweet currentTweet = new Tweet(user, "The greatest glory in living lies not in never falling, but in rising every time we fall.", 100000000);
        Tweet resultTweet1 = new Tweet(user, "The way to get started is to quit talking and begin doing.", 100000000);
        Tweet resultTweet2 = new Tweet(user, "Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking.", 100000000);
        Tweet resultTweet3 = new Tweet(user, "If life were predictable it would cease to be life, and be without flavor.", 100000000);


        // Setup request objects to use in the tests
        validRequest = new StoryTweetsRequest(user, 3, null, "authTokenGoesHere");
        invalidRequest = new StoryTweetsRequest(null, 0, null, "authTokenGoesHere");

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StoryTweetsResponse(Arrays.asList(resultTweet1, resultTweet2, resultTweet3), false);
        failureResponse = new StoryTweetsResponse("An exception occurred");

        StoryTweetsServiceImplSpy = Mockito.mock(StoryTweetsServiceImpl.class);
        Mockito.when(StoryTweetsServiceImplSpy.getStoryTweets(validRequest)).thenReturn(successResponse);
        Mockito.when(StoryTweetsServiceImplSpy.getStoryTweets(invalidRequest)).thenReturn(failureResponse);
    }

    /**
     * Verify that for successful requests the {@link StoryTweetsServiceImpl #getStoryTweets(StoryTweetsRequest)}
     * method returns the same result as the {@link edu.byu.cs.tweeter.server.dao.StoryTweetsDAO}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeedTweets_validRequest_correctResponse() throws IOException {
        StoryTweetsResponse response = StoryTweetsServiceImplSpy.getStoryTweets(validRequest);
        Assertions.assertEquals(successResponse, response);
    }


    /**
     * Verify that for failed requests the {@link StoryTweetsServiceImpl #getFeedTweets(FeedTweetsRequest)}
     * method returns the same result as the {@link edu.byu.cs.tweeter.server.dao.StoryTweetsDAO}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeedTweets_invalidRequest_returnsNoFeedTweets() throws IOException {
        StoryTweetsResponse response = StoryTweetsServiceImplSpy.getStoryTweets(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}

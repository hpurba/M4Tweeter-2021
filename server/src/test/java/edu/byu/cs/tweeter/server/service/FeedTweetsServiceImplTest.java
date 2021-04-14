package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;

public class FeedTweetsServiceImplTest {

    private FeedTweetsRequest validRequest;
    private FeedTweetsRequest invalidRequest;

    private FeedTweetsResponse successResponse;
    private FeedTweetsResponse failureResponse;

    private FeedTweetsServiceImpl feedTweetsServiceSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException {
//        Tweet currentTweet = new Tweet("@TestUser", "The greatest glory in living lies not in never falling, but in rising every time we fall.", "userName");
//        Tweet resultTweet1 = new Tweet("@TestUser", "The way to get started is to quit talking and begin doing.", "userName");
//        Tweet resultTweet2 = new Tweet("@TestUser", "Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking.", "userName");
//        Tweet resultTweet3 = new Tweet("@TestUser", "If life were predictable it would cease to be life, and be without flavor.", "userName");


        // Requests (Valid and Invalid)
//        validRequest = new FeedTweetsRequest(currentTweet, 3, null);
//        invalidRequest = new FeedTweetsRequest(null, 0, null);

//        successResponse = new FeedTweetsResponse(Arrays.asList(resultTweet1, resultTweet2, resultTweet3), false);
        failureResponse = new FeedTweetsResponse("An exception occurred");

        // Create a FeedTweetsService instance and wrap it with a spy that will use the mock service
        feedTweetsServiceSpy = Mockito.mock(FeedTweetsServiceImpl.class);
        Mockito.when(feedTweetsServiceSpy.getFeedTweets(validRequest)).thenReturn(successResponse);
        Mockito.when(feedTweetsServiceSpy.getFeedTweets(invalidRequest)).thenReturn(failureResponse);
    }

    /**
     * Verify that for successful requests the {@link FeedTweetsServiceImpl #getFeedTweets(FeedTweetsRequest)}
     * method returns the same result as the {@link edu.byu.cs.tweeter.server.dao.FeedTweetsDAO}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeedTweets_validRequest_correctResponse() throws IOException {
        FeedTweetsResponse response = feedTweetsServiceSpy.getFeedTweets(validRequest);
        Assertions.assertEquals(successResponse, response);
    }


    /**
     * Verify that for failed requests the {@link FeedTweetsServiceImpl #getFeedTweets(FeedTweetsRequest)}
     * method returns the same result as the {@link edu.byu.cs.tweeter.server.dao.FeedTweetsDAO}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeedTweets_invalidRequest_returnsNoFeedTweets() throws IOException {
        FeedTweetsResponse response = feedTweetsServiceSpy.getFeedTweets(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}

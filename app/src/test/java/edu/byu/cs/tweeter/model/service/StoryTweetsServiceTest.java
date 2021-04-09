package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;

class StoryTweetsServiceTest {

    private StoryTweetsRequest validRequest;
    private StoryTweetsRequest invalidRequest;

    private StoryTweetsResponse successResponse;
    private StoryTweetsResponse failureResponse;

    private StoryTweetsService storyTweetsServiceSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException {
        Tweet currentTweet = new Tweet("@TestUser", "The greatest glory in living lies not in never falling, but in rising every time we fall.", "userName");
        Tweet resultTweet1 = new Tweet("@TestUser", "The way to get started is to quit talking and begin doing.", "userName");
        Tweet resultTweet2 = new Tweet("@TestUser", "Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking.", "userName");
        Tweet resultTweet3 = new Tweet("@TestUser", "If life were predictable it would cease to be life, and be without flavor.", "userName");


        // Setup request objects to use in the tests
        validRequest = new StoryTweetsRequest(currentTweet, 3, null);
        invalidRequest = new StoryTweetsRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StoryTweetsResponse(Arrays.asList(resultTweet1, resultTweet2, resultTweet3), false);
        failureResponse = new StoryTweetsResponse("An exception occurred");

        storyTweetsServiceSpy = Mockito.spy(new StoryTweetsService());
        Mockito.when(storyTweetsServiceSpy.getStoryTweets(validRequest)).thenReturn(successResponse);
        Mockito.when(storyTweetsServiceSpy.getStoryTweets(invalidRequest)).thenReturn(failureResponse);
    }

    /**
     * Verify that for successful requests the {@link StoryTweetsService #getStoryTweets(StoryTweetsRequest)}
     * method returns the same result as the {@link ServerFacadeOriginalM2}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeedTweets_validRequest_correctResponse() throws IOException {
        StoryTweetsResponse response = storyTweetsServiceSpy.getStoryTweets(validRequest);
        Assertions.assertEquals(successResponse, response);
    }


    /**
     * Verify that for failed requests the {@link StoryTweetsService #getFeedTweets(FeedTweetsRequest)}
     * method returns the same result as the {@link ServerFacadeOriginalM2}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeedTweets_invalidRequest_returnsNoFeedTweets() throws IOException {
        StoryTweetsResponse response = storyTweetsServiceSpy.getStoryTweets(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
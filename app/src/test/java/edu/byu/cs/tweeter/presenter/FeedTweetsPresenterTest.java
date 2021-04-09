package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.service.FeedTweetsService;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;

class FeedTweetsPresenterTest {


    private FeedTweetsRequest request;
    private FeedTweetsResponse response;
    private FeedTweetsService mockFeedTweetsService;
    private FeedTweetsPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {

        Tweet tweet1 = new Tweet("@AmyAmes", "Yooo I'm Amy.", "userName");
        Tweet tweet2 = new Tweet("@BobBobson", "Welcome to Bob paradise", "userName");
        Tweet tweet3 = new Tweet("@BonnieBeatty", "I don't have anything to say", "userName");
        int limit = 3;


        request = new FeedTweetsRequest(tweet1, limit, tweet3);
        response = new FeedTweetsResponse(Arrays.asList(tweet1, tweet2, tweet3), false);

        // Create a mock FollowingService
        mockFeedTweetsService = Mockito.mock(FeedTweetsService.class);
        Mockito.when(mockFeedTweetsService.getFeedTweets(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FeedTweetsPresenter(new FeedTweetsPresenter.View() {
            @Override
            public Tweet getTweet() {
                return null;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public Tweet getLastTweet() {
                return null;
            }
        }));
        Mockito.when(presenter.getFeedTweetsService()).thenReturn(mockFeedTweetsService);
    }

    @Test
    public void testGetFeedTweets_returnsServiceResult() throws IOException {
        Mockito.when(mockFeedTweetsService.getFeedTweets(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
//        Assertions.assertEquals(response, presenter.getFeedTweets(request));
        Assertions.assertEquals(response, presenter.getFeedTweets());
    }

    @Test
    public void testGetFeedTweets_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockFeedTweetsService.getFeedTweets(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
//            presenter.getFeedTweets(request);
            presenter.getFeedTweets();
        });
    }
}
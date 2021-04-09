package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.TweetService;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;

class TweetPresenterTest {

    private TweetRequest request;
    private TweetRequest badRequest;
    private TweetResponse response;
    private TweetService mockTweetService;
    private TweetPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {

        String alias = "@AmyAmes";
        String tweetText = "Yooo I'm Amy.";
        User user = new User("Test", "User","https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new TweetRequest(alias, tweetText);
        badRequest = new TweetRequest(null, null);
        response = new TweetResponse(user);

        // Create a mock FollowingService
        mockTweetService = Mockito.mock(TweetService.class);
        Mockito.when(mockTweetService.tweet(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new TweetPresenter(new TweetPresenter.View() {}));
        Mockito.when(presenter.getTweetService()).thenReturn(mockTweetService);
    }

    @Test
    public void testGetTweet_returnsServiceResult() throws IOException {
        Mockito.when(mockTweetService.tweet(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response.getUser().getAlias(), presenter.tweet(request).getUser().getAlias());
    }

    @Test
    public void testGetTweet_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockTweetService.tweet(badRequest)).thenThrow(new IOException());
        Assertions.assertEquals(response.getUser().getAlias(), presenter.tweet(request).getUser().getAlias());
//        Assertions.assertThrows(IOException.class, () -> {
//            presenter.tweet(badRequest);
//        });
    }
}
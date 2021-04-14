//package edu.byu.cs.tweeter.presenter;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import edu.byu.cs.tweeter.model.domain.Tweet;
//import edu.byu.cs.tweeter.model.service.StoryTweetsService;
//import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
//import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
//
//class StoryTweetsPresenterTest {
//
//    private StoryTweetsRequest request;
//    private StoryTweetsResponse response;
//    private StoryTweetsService mockStoryTweetsService;
//    private StoryTweetsPresenter presenter;
//
//    @BeforeEach
//    public void setup() throws IOException {
//        Tweet tweet1 = new Tweet("@TestUser", "The greatest glory in living lies not in never falling, but in rising every time we fall.", "userName");
//        Tweet tweet2 = new Tweet("@TestUser", "The way to get started is to quit talking and begin doing.", "userName");
//        Tweet tweet3 = new Tweet("@TestUser", "Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking.", "userName");
//        int limit = 3;
//
//
//
//        request = new StoryTweetsRequest(tweet1, limit, tweet3);
//        response = new StoryTweetsResponse(Arrays.asList(tweet1, tweet2, tweet3), false);
//
//        // Create a mock FollowingService
//        mockStoryTweetsService = Mockito.mock(StoryTweetsService.class);
//        Mockito.when(mockStoryTweetsService.getStoryTweets(request)).thenReturn(response);
//
//        // Wrap a FollowingPresenter in a spy that will use the mock service.
//        presenter = Mockito.spy(new StoryTweetsPresenter(new StoryTweetsPresenter.View() {}));
//        Mockito.when(presenter.getStoryTweetsService()).thenReturn(mockStoryTweetsService);
//    }
//
//    @Test
//    public void testGetFeedTweets_returnsServiceResult() throws IOException {
//        Mockito.when(mockStoryTweetsService.getStoryTweets(request)).thenReturn(response);
//
//        // Assert that the presenter returns the same response as the service (it doesn't do
//        // anything else, so there's nothing else to test).
//        Assertions.assertEquals(response, presenter.getStoryTweets(request));
//    }
//
//    @Test
//    public void testGetFeedTweets_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
//        Mockito.when(mockStoryTweetsService.getStoryTweets(request)).thenThrow(new IOException());
//
//        Assertions.assertThrows(IOException.class, () -> {
//            presenter.getStoryTweets(request);
//        });
//    }
//
//}
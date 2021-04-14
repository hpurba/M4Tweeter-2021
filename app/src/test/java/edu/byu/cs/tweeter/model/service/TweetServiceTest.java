//package edu.byu.cs.tweeter.model.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//
//import edu.byu.cs.tweeter.model.domain.Tweet;
//import edu.byu.cs.tweeter.model.service.request.TweetRequest;
//import edu.byu.cs.tweeter.model.service.response.TweetResponse;
//
//class TweetServiceTest {
//
//
//    private TweetRequest validRequest;
//    private TweetRequest invalidRequest;
//
//    private TweetResponse successResponse;
//    private TweetResponse failureResponse;
//
//    private TweetService tweetServiceSpy;
//
//    /**
//     * Create a TweetService spy that uses a mock ServerFacade to return known responses to
//     * requests.
//     */
//    @BeforeEach
//    public void setup() throws IOException {
//        String alias = "@hpurba";
//        String tweetText = "I am Hikaru!";
//
//        Tweet tweet1 = new Tweet("@AllenAnderson", "I am Allen! Nice to meet you all!", "userName");
//
//        User user = new User("Test", "User","https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//
//        // Setup request objects to use in the tests
//        validRequest = new TweetRequest(alias, tweetText);
//        invalidRequest = new TweetRequest(null, null);
//
//        // Setup a mock ServerFacade that will return known responses
//        successResponse = new TweetResponse(user); // success is true and message is null
//        failureResponse = new TweetResponse((User) null);
//
//        tweetServiceSpy = Mockito.spy(new TweetService());
//        Mockito.when(tweetServiceSpy.tweet(validRequest)).thenReturn(successResponse);
//        Mockito.when(tweetServiceSpy.tweet(invalidRequest)).thenReturn(failureResponse);
//    }
//
//    /**
//     * Verify that for successful requests the {@link TweetService #tweet(TweetRequest)}
//     * method returns the same result as the {@link ServerFacadeOriginalM2}.
//     * .
//     *
//     * @throws IOException if an IO error occurs.
//     */
//    @Test
//    public void testGetRegisterStatus_validRequest_correctResponse() throws IOException {
//        TweetResponse response = tweetServiceSpy.tweet(validRequest);
//        Assertions.assertEquals(successResponse, response);
//    }
//
//
//    /**
//     * Verify that for failed requests the {@link TweetService #tweet(TweetRequest)}
//     * method returns the same result as the {@link ServerFacadeOriginalM2}.
//     *
//     * @throws IOException if an IO error occurs.
//     */
//    @Test
//    public void testRegisterStatus_invalidRequest_returnsNoLoginStatus() throws IOException {
//        TweetResponse response = tweetServiceSpy.tweet(invalidRequest);
//        Assertions.assertEquals(failureResponse, response);
//    }
//
//}
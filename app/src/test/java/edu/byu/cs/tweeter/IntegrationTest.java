package edu.byu.cs.tweeter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FeedTweetsService;
import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.FollowingStatusService;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.StoryTweetsService;
import edu.byu.cs.tweeter.model.service.TweetService;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;


import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private String username = "@HikaruPurba";
    private String password = "Password";
    private String firstName = "Hikaru";
    private String lastName = "Purba";

    private User user = new User(firstName, lastName, username, MALE_IMAGE_URL);

    private final Tweet tweet1 = new Tweet("@AllenAnderson", "I am Allen! Nice to meet you all!", "Allen Anderson");
    private final Tweet tweet2 = new Tweet("@AmyAmes", "Yooo I'm Amy.", "Amy Ames");
    private final Tweet tweet3 = new Tweet("@BobBobson", "Welcome to Bob paradise", "Bob Bobson");
    private final Tweet tweet4 = new Tweet("@BonnieBeatty", "I don't have anything to say", "Bonnie Beatty");
    private final Tweet tweet5 = new Tweet("@ChrisColston", "Motorycles are cool", "Chris Colston");
    private final Tweet tweet6 = new Tweet("@CindyCoats", "I love physics", "Cindy Coats");
    private final Tweet tweet7 = new Tweet("@DanDonaldson", "Do I look like a duck to you?", "Dan Donaldson");
    private final Tweet tweet8 = new Tweet("@DeeDempsey", "Doooo doooooooo doodly dooo", "Dee Dempsey");

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User testUser = new User("test", "user", "@TestUser", MALE_IMAGE_URL);

    @Test
    public void integrationTest() throws IOException, TweeterRemoteException {

        LoginResponse expect = new LoginResponse(user, new AuthToken());

        LoginRequest request = new LoginRequest(username, password);
        LoginService loginService = new LoginService();
        LoginResponse response = loginService.login(request);

        assertNotNull(response);
        assertEquals(expect.getUser(), response.getUser());
        assertEquals(expect.getMessage(), response.getMessage());


        FeedTweetsRequest feedTweetsRequest = new FeedTweetsRequest(tweet1, 8, null);
        FeedTweetsResponse feedTweetsResponseExpected = new FeedTweetsResponse(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8), true);
        FeedTweetsService feedTweetsService = new FeedTweetsService();
        FeedTweetsResponse feedTweetsResponse = feedTweetsService.getFeedTweets(feedTweetsRequest);


        assertNotNull(feedTweetsResponse);
        assertEquals(feedTweetsResponseExpected.getTweets(), feedTweetsResponse.getTweets());
        assertEquals(feedTweetsResponseExpected.getHasMorePages(), feedTweetsResponse.getHasMorePages());



        FollowerRequest followerRequest = new FollowerRequest(user, 8, null);
        FollowerResponse followerResponseExpected = new FollowerResponse(Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8), true);
        FollowerService followerService = new FollowerService();
        FollowerResponse followerResponse = followerService.getFollowers(followerRequest);

        assertNotNull(followerResponse);
        assertEquals(followerResponseExpected.getFollowers(),followerResponse.getFollowers());
        assertEquals(followerResponseExpected.getHasMorePages(), followerResponseExpected.getHasMorePages());

        FollowingRequest followingRequest = new FollowingRequest(user, 8, null);
        FollowingResponse followingResponseExpected = new FollowingResponse(Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8), true);
        FollowingService followingService = new FollowingService();
        FollowingResponse followingResponse = followingService.getFollowees(followingRequest);

        assertNotNull(followingRequest);
        assertEquals(followingResponseExpected.getFollowees(),followingResponse.getFollowees());
        assertEquals(followingResponseExpected.getHasMorePages(), followingResponse.getHasMorePages());

        FollowingStatusRequest followingStatusRequest = new FollowingStatusRequest(user, false);
        FollowingStatusResponse followingStatusResponseExpected = new FollowingStatusResponse(username, false);
        FollowingStatusService followingStatusService = new FollowingStatusService();
        FollowingStatusResponse followingStatusResponse = followingStatusService.getFollowingStatus(followingStatusRequest);

        assertNotNull(response);
        assertEquals(followingStatusResponseExpected.getFollowing(), followingStatusResponse.getFollowing());


        followingStatusRequest = new FollowingStatusRequest(user, true);
        followingStatusResponseExpected = new FollowingStatusResponse(username, true);
        followingStatusService = new FollowingStatusService();
        followingStatusResponse = followingStatusService.getFollowingStatus(followingStatusRequest);

        assertNotNull(response);
        assertEquals(followingStatusResponseExpected.getFollowing(), followingStatusResponse.getFollowing());

        LogoutRequest logoutRequest = new LogoutRequest(username);
        LogoutResponse logoutResponseExpected = new LogoutResponse(true);
        LogoutService logoutService = new LogoutService();
        LogoutResponse logoutResponse = logoutService.logout(logoutRequest);
        assertNotNull(logoutResponse);
        assertEquals(logoutResponseExpected.getMessage(), logoutResponse.getMessage());

        RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, username, password, null);
        RegisterResponse registerResponseExpected = new RegisterResponse(new User("firstname", "lastname", "@firstnamelastname", MALE_IMAGE_URL), new AuthToken());
        RegisterService registerService = new RegisterService();
        RegisterResponse registerResponse = registerService.register(registerRequest);

        assertNotNull(registerResponse);
        assertEquals(registerResponseExpected.getUser(), registerResponse.getUser());

        StoryTweetsRequest storyTweetsRequest = new StoryTweetsRequest(tweet1, 8, null);
        StoryTweetsResponse storyTweetsResponseExpected = new StoryTweetsResponse(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8), true);
        StoryTweetsService storyTweetsService = new StoryTweetsService();
        StoryTweetsResponse storyTweetsResponse = storyTweetsService.getStoryTweets(storyTweetsRequest);


        assertNotNull(storyTweetsResponse);
        assertEquals(storyTweetsResponseExpected.getTweets().size(), storyTweetsResponse.getTweets().size());
        assertEquals(storyTweetsResponseExpected.getHasMorePages(), storyTweetsResponse.getHasMorePages());


        TweetRequest tweetRequest = new TweetRequest(username, "Test");
        TweetResponse tweetResponseExpected = new TweetResponse(testUser);
        TweetService tweetService = new TweetService();
        TweetResponse tweetResponse = tweetService.tweet(tweetRequest);

        assertEquals(tweetResponseExpected.getUser(), tweetResponse.getUser());

    }
}
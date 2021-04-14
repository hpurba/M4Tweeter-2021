package edu.byu.cs.tweeter.model.net;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server go through
 * this class.
 */
public class ServerFacade {
    // Server URL: This is the invoke URL of my API on AWS. It can be found by going to your API in AWS, clicking
    // on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://yz55y6hm7b.execute-api.us-west-2.amazonaws.com/dev";

    // Instantiate the Client Communicator with the SERVER_URL
    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a Login of a user.
     * If successful, returns the logged in user and an auth token in the LoginResponse object.
     *
     * @param request   LoginRequest Object which contains all information needed to perform a login.
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for logging a user in.
     * @return          LogoutResponse Object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
//        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
//        if(response.isSuccess()) {
//            return response;
//        } else {
//            // TODO: potentially consider changing this to handle somewhere the error 400 and 500 response codes.
//            throw new RuntimeException(response.getMessage());
//        }
    }

    /**
     * Performs a Logout of the current user.
     * If successful, returns a LogoutResponse with success = true and message = null.
     *
     * @param request   LogoutRequest Object which contains all information needed to perform a logout.
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for logging a user out.
     * @return          LogoutResponse object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public LogoutResponse logout(LogoutRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LogoutResponse response = clientCommunicator.doPost(urlPath, request, null, LogoutResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Performs a register of a new user.
     * If successful, this method returns a RegisterResponse Object which contains the newly logged in user and an auth token.
     * Included in the RegisterRequest Object is the (potential) newly created user's firstName, lastName, alias (username),
     * password, and byteArray (byte[]).
     *
     * @param request   RegisterRequest Object which contains all the necessary information to register a new user and log them in.
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for registering a new user.
     * @return          RegisterResponse Object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public RegisterResponse register(RegisterRequest request, String urlPath) throws IOException, TweeterRemoteException {
        RegisterResponse response = clientCommunicator.doPost(urlPath, request, null, RegisterResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Performs a retrieval of followees of a user (Can be the current user or some other user).
     * If successful, this method returns a FollowingResponse Object which contains List<User> which are
     * followees of the user (a follower) specified in the request.
     * Also in the FollowingRequest is the limit for the number of followees returned to allow for pagination.
     * - This also means it contains the lastFollowee of the previous request.
     *
     * @param request   FollowingRequest Object which contains all information necessary to get the followees of a user (the follower).
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for getting followees of a user.
     * @return          FollowingResponse object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public FollowingResponse getFollowees(FollowingRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        FollowingResponse response = clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Performs a retrieval of followers of a user (Can be the current user or some other user).
     * If successful, this method returns a FollowerResponse Object which contains List<User> which are
     * followers of the user (a followee) specified in the request.
     * Also in the FollowerRequest is the limit for the number of followees returned to allow for pagination.
     *  - This also means it contains the lastFollower of the previous request.
     *
     * @param request   FollowerRequest Object which contains all information necessary to get the followers of a user (the followee).
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for getting followers of a user.
     * @return          FollowerResponse Object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public FollowerResponse getFollowers(FollowerRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowerResponse response = clientCommunicator.doPost(urlPath, request, null, FollowerResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }

    }

    /**
     * Performs a retrieval of feed tweets of the user. Only the feed of the logged in user will have
     * a feed of followee (tweets of users he/she if following) tweets visible.
     * If successful, this method returns a FeedTweetsResponse Object which contains List<Tweet>.
     * Included in the FeedTweetsRequest is the limit for the number of feed tweets to be returned to allow for pagination.
     * - This also means it contains the lastTweet of the previous request.
     *
     * *** Remember only the currently logged in user can see his/her own feed.
     *
     * @param request   FeedTweetsRequest Object which contains all the necessary information to get the feed of tweets for the current user.
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for getting the user's feed of tweets.
     * @return          FeedTweetsResponse Object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public FeedTweetsResponse getFeedTweets(FeedTweetsRequest request, String urlPath) throws IOException, TweeterRemoteException {

        FeedTweetsResponse response = clientCommunicator.doPost(urlPath, request, null, FeedTweetsResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Performs a retrieval of story tweets of a user (Can be the current user or some other user).
     * If successful, this method returns a StoryTweetsResponse Object which contains List<Tweet>.
     * Included in the StoryTweetsRequest is the limit for the number of story tweets to be returned to allow for pagination.
     *   - This also means it contains the lastTweet of the previous request.
     *
     * *** Remember any user can see another user's story.
     *
     * @param request   StoryTweetsRequest Object which contains all the necessary information to get the story of tweets for a user.
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for getting the user's story of tweets.
     * @return          StoryTweetsResponse Object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public StoryTweetsResponse getStoryTweets(StoryTweetsRequest request, String urlPath) throws IOException, TweeterRemoteException {

        StoryTweetsResponse response = clientCommunicator.doPost(urlPath, request, null, StoryTweetsResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Performs a post of a tweet by the currently logged in user.
     * If successful, this method will post a tweet (enter the tweet into dynamoDB) and return a TweetResponse Object which
     * indicates if the action was successful or not.
     * Included in the TweetRequest Object is the username/alias of the currently tweeting user (logged in user) and tweetText.
     *
     * @param request   TweetRequest Object which contains all the necessary information to make a tweet by the user.
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for making a tweet.
     * @return          TweetResponse Object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public TweetResponse tweet(TweetRequest request, String urlPath) throws IOException, TweeterRemoteException {
        TweetResponse response = clientCommunicator.doPost(urlPath, request, null, TweetResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Performs a change of following status of a user by the currently logged in user to FOLLOWING.
     * If successful, this method will make the currently logged in user follow another user indicated by "otherPersonUsername".
     * Included in the FollowingStatusRequest is a boolean "isFollowing" which contains the following or un-following that is being requested.
     *
     * @param request   FollowingStatusRequest Object which contains all the necessary information to make a change to follow another user.
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for making a request to follow another user and change following status.
     * @return          FollowingStatusResponse Object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public FollowingStatusResponse changeToFollow(FollowingStatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowingStatusResponse response = clientCommunicator.doPost(urlPath, request, null, FollowingStatusResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Performs a change of following status of a user by the currently logged in user to UN-FOLLOWING.
     * If successful, this method will make the currently logged in user un-follow another user indicated by "otherPersonUsername".
     * Included in the FollowingStatusRequest is a boolean "isFollowing" which contains the following or un-following that is being requested.
     *
     * @param request   FollowingStatusRequest Object which contains all the necessary information to make a change to un-follow another user.
     * @param urlPath   urlPath Extension to the SERVER_URL (base URL) for making a request to un-follow another user and change following status.
     * @return          FollowingStatusResponse Object from the AWS server.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public FollowingStatusResponse changeToUnFollow(FollowingStatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowingStatusResponse response = clientCommunicator.doPost(urlPath, request, null, FollowingStatusResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Grabs another user (other than the current one)
     * @param request
     * @param urlPath
     * @return
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public GetUserResponse getOtherUser(GetUserRequest request, String urlPath) throws IOException, TweeterRemoteException {
        GetUserResponse response = clientCommunicator.doPost(urlPath, request, null, GetUserResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
}
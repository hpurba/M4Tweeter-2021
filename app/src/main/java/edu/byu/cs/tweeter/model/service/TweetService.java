package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.ITweetService;

public class TweetService extends BaseService implements ITweetService {
    // The url_path extension for tweet. (Can be found in AWS console -> API:Tweeter -> Stages -> dev tab)
    private static final String URL_PATH = "/tweet";

    // Tweet Request and Response Objects.
    TweetRequest tweetRequest;
    TweetResponse tweetResponse;

    /**
     * Performs a posting of a tweet for the user.
     *
     * @param request   TweetRequest Object.
     * @return          TweetResponse Object.
     * @throws IOException
     */
    public TweetResponse tweet(TweetRequest request) throws IOException {
        this.tweetRequest = request;
        processServiceRequest();
        return tweetResponse;
    }

    /**
     * This is the primary method in the Template pattern of the BaseService Class.
     * This will post a tweet for a user by calling the tweet method in the server facade
     * by passing it the provided TweetRequest (which is first passed into the tweet method above).
     * Returning the TweetResponse Object is handled in the tweet() method.
     *
     * @throws IOException
     * @throws TweeterRemoteException
     */
    @Override
    public void doServiceSpecificTask() throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        this.tweetResponse = serverFacade.tweet(tweetRequest, URL_PATH);
    }
}
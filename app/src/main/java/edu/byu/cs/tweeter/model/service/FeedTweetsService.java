package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.IFeedTweetsService;

/**
 * FeedTweetsService extends the BaseService Abstract Class to get the Tweets for the Feed.
 */
public class FeedTweetsService extends BaseService implements IFeedTweetsService {
    // The url_path extension for retrieving tweets for the user's feed. (Can be found in AWS console -> API:Tweeter -> Stages -> dev tab)
    private static final String URL_PATH = "/feedtweets";

    // Feed of Tweets Response and Request Objects.
    FeedTweetsResponse feedTweetsResponse;
    FeedTweetsRequest feedTweetsRequest;

    /**
     * This is called to get the Tweets for the user's Feed.
     * Takes a FeedTweetsRequest Object as the parameter and returns a FeedTweetsResponse Object.
     *
     * @param request   FeedTweetsRequest Object.
     * @return          FeedTweetsResponse Object.
     * @throws IOException
     */
    public FeedTweetsResponse getFeedTweets(FeedTweetsRequest request) throws IOException {
        this.feedTweetsRequest = request;
        processServiceRequest();    // Sets up the ServerFacade and calls the doServiceSpecificTask.
        return feedTweetsResponse;
    }

    /**
     * This is the primary method in the Template pattern of the BaseService Class.
     * This will get the Feed Tweets from the server facade using the provided
     * feedTweetsRequest (which is first passed into getFeedTweets).
     *
     * @throws IOException
     * @throws TweeterRemoteException
     */
    @Override
    public void doServiceSpecificTask() throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        this.feedTweetsResponse = serverFacade.getFeedTweets(feedTweetsRequest, URL_PATH);

        // TODO: Find out if loading images for each feed tweet is necessary
        //        if(feedTweetsResponse.isSuccess()) {
        //            loadImage(feedTweetsResponse.getUser());
        //        }
    }
}
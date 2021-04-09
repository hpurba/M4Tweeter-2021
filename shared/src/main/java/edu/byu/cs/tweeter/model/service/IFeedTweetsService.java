package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;

public interface IFeedTweetsService {
    FeedTweetsResponse getFeedTweets(FeedTweetsRequest request) throws IOException, TweeterRemoteException;

}

package edu.byu.cs.tweeter.model.service.serviceInterfaces;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;

public interface IStoryTweetsService {
    StoryTweetsResponse getStoryTweets(StoryTweetsRequest request) throws IOException, TweeterRemoteException;
}

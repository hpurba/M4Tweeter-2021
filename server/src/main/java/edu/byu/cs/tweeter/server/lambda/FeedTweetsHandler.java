package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;
import edu.byu.cs.tweeter.server.service.FeedTweetsServiceImpl;

public class FeedTweetsHandler implements RequestHandler<FeedTweetsRequest, FeedTweetsResponse> {
    @Override
    public FeedTweetsResponse handleRequest(FeedTweetsRequest feedTweetsRequest, Context context) {
        FeedTweetsServiceImpl feedTweetsService = new FeedTweetsServiceImpl();
        return feedTweetsService.getFeedTweets(feedTweetsRequest);
    }
}
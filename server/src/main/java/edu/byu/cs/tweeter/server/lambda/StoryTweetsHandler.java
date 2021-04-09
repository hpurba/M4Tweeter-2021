package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
import edu.byu.cs.tweeter.server.service.StoryTweetsServiceImpl;

public class StoryTweetsHandler implements RequestHandler<StoryTweetsRequest, StoryTweetsResponse> {
    @Override
    public StoryTweetsResponse handleRequest(StoryTweetsRequest request, Context context) {
        StoryTweetsServiceImpl service = new StoryTweetsServiceImpl();
        return service.getStoryTweets(request);
    }
}

package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.serviceInterfaces.IFeedTweetsService;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;
import edu.byu.cs.tweeter.server.dao.FeedTweetsDAO;

public class FeedTweetsServiceImpl extends ServiceImpl implements IFeedTweetsService {

    public FeedTweetsResponse getFeedTweets(FeedTweetsRequest request) {
        checkAuthorizationToken(request.getAuthToken());
        return feedTweetsDAO().getFeedTweets(request);
    }

    FeedTweetsDAO feedTweetsDAO() { return new FeedTweetsDAO(); }
}

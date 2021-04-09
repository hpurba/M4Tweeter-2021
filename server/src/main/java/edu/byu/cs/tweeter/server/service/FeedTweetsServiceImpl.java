package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.IFeedTweetsService;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;
import edu.byu.cs.tweeter.server.dao.FeedTweetsDAO;

public class FeedTweetsServiceImpl implements IFeedTweetsService {

    public FeedTweetsResponse getFeedTweets(FeedTweetsRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getLimit() < 0) {
            throw new RuntimeException("[BadRequest500] 500");
        }
        return feedTweetsDAO().getFeedTweets(request);
    }

    FeedTweetsDAO feedTweetsDAO() { return new FeedTweetsDAO(); }
}

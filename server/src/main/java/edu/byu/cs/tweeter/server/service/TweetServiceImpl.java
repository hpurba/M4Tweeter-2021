package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.ITweetService;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;
import edu.byu.cs.tweeter.server.dao.TweetDAO;

public class TweetServiceImpl implements ITweetService {

    public TweetResponse tweet(TweetRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getTweetText() != null && request.getUsername() == null) {
            throw new RuntimeException("[BadRequest500] 500");
        }
        return tweetDAO().tweet(request);
    }

    TweetDAO tweetDAO() { return new TweetDAO(); }
}

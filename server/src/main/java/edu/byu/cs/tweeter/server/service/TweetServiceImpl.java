package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.serviceInterfaces.ITweetService;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;
import edu.byu.cs.tweeter.server.dao.StoriesDAO;

public class TweetServiceImpl extends ServiceImpl implements ITweetService {

    public TweetResponse tweet(TweetRequest request) {
        checkAuthorizationToken(request.getAuthToken());    // this will throw an exception if the token is not valid.
        return storiesDAO().postTweet(request);
    }

    StoriesDAO storiesDAO() { return new StoriesDAO(); }
}

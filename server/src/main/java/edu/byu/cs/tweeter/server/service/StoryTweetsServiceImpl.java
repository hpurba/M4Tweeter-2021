package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.IStoryTweetsService;
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
import edu.byu.cs.tweeter.server.dao.StoriesDAO;

public class StoryTweetsServiceImpl extends ServiceImpl implements IStoryTweetsService {

    public StoryTweetsResponse getStoryTweets(StoryTweetsRequest request) {
        checkAuthorizationToken(request.getAuthToken());    // this will throw an exception if the token is not valid.
        return storiesDAO().getStoryTweets(request);
    }

    StoriesDAO storiesDAO() { return new StoriesDAO(); }
}

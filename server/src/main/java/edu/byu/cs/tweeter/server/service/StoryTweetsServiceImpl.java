package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.IStoryTweetsService;
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
import edu.byu.cs.tweeter.server.dao.StoryTweetsDAO;

public class StoryTweetsServiceImpl implements IStoryTweetsService {

    public StoryTweetsResponse getStoryTweets(StoryTweetsRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getLimit() < 0) {
            throw new RuntimeException("[BadRequest500] 500");
        }
        return storyTweetsDAO().getStoryTweets(request);
    }

    StoryTweetsDAO storyTweetsDAO() { return new StoryTweetsDAO(); }
}

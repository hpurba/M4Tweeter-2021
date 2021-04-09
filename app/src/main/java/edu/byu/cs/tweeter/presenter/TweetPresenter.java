package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.StoryTweetsService;
import edu.byu.cs.tweeter.model.service.TweetService;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;

public class TweetPresenter {


    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public TweetPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a tweet request.
     *
     * @param tweetRequest the request.
     */
    public TweetResponse tweet(TweetRequest tweetRequest) throws IOException {
        TweetService tweetService = new TweetService();
        return tweetService.tweet(tweetRequest);
    }

    TweetService getTweetService() {
        return new TweetService();
    }
}

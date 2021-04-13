package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FeedTweetsService;
import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;

public class FeedTweetsPresenter {

    private final View view;

    /**
     * This is the interface for the presenter's view (FeedTweetsFragment).
     * It is the interface by which this presenter can communicate with it's view (call it's methods).
     * Methods listed here should be used only for retrieval, or raising an event (ex: change a button status).
     *  - If needed, specify methods here that will be called on the view in response to model updates.
     */
    public interface View {
        User getUser();
        int getPageSize();
        Tweet getLastTweet();
        String getAuthToken();
    }

    /**
     * Creates an instance of a FeedTweetsPresenter with the provided view (should be FeedTweetsFragment).
     * @param view View, which should be a FeedTweetsFragment view for which this class is the presenter.
     */
    public FeedTweetsPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes the retrieval of the feed of tweets.
     * @return  FeedTweetsResponse Object. Contains tweets made by followees of the current user.
     * @throws IOException
     */
    public FeedTweetsResponse getFeedTweets() throws IOException {
        FeedTweetsRequest request = new FeedTweetsRequest(view.getUser(), view.getPageSize(), view.getLastTweet(), view.getAuthToken());
        FeedTweetsService feedTweetsService = getFeedTweetsService();
        return feedTweetsService.getFeedTweets(request);
    }

    /**
     * Performs a retrieval of a new FeedTweetsService Object.
     * Returns an instance of {@link FeedTweetsService}. For testing purposes, all usages of FeedTweetsService
     * should get their FeedTweetsService instance from this method to allow for mocking of the instance.
     *
     * @return  A new FeedTweetsService Object.
     */
    FeedTweetsService getFeedTweetsService() {
        return new FeedTweetsService();
    }
}
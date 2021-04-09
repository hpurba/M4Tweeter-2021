package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class FollowerPresenter {

    private final View view;

    /**
     * This is the interface for the presenter's view (FollowersFragment).
     * It is the interface by which this presenter can communicate with it's view (call it's methods).
     * Methods listed here should be used only for retrieval, or raising an event (ex: change a button status).
     *  - If needed, specify methods here that will be called on the view in response to model updates.
     */
    public interface View {
        User getUser();
        int getPageSize();
        User getLastFollower();
    }

    /**
     * Creates an instance of a FollowerPresenter with the provided view (should be FollowersFragment).
     * @param view View, which should be a FollowersFragment view for which this class is the presenter.
     */
    public FollowerPresenter(View view) {
        this.view = view;
    }

    /**
     * Retrieves the followers of a user.
     * @return  FollowerResponse Object. Contains List<User> followers of a user.
     * @throws IOException
     */
    public FollowerResponse getFollowers() throws IOException {
        FollowerRequest request = new FollowerRequest(view.getUser(), view.getPageSize(), view.getLastFollower());
        FollowerService followerService = getFollowerService();
        return followerService.getFollowers(request);
    }

    /**
     * Performs a retrieval of a new FollowerService Object.
     * Returns an instance of {@link FollowerService}. For testing purposes, all usages of FollowerService
     * should get their FollowerService instance from this method to allow for mocking of the instance.
     *
     * @return  A new FollowerService Object.
     */
    FollowerService getFollowerService() {
        return new FollowerService();
    }
}
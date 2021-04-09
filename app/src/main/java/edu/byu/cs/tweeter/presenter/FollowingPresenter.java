package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
public class FollowingPresenter {

    private final View view;

    /**
     * This is the interface for the presenter's view (FollowingFragment).
     * It is the interface by which this presenter can communicate with it's view (call it's methods).
     * Methods listed here should be used only for retrieval, or raising an event (ex: change a button status).
     *  - If needed, specify methods here that will be called on the view in response to model updates.
     */
    public interface View {
        User getUser();
        int getPageSize();
        User getLastFollowee();
    }

    /**
     * Creates an instance of a FollowingPresenter with the provided view (should be FollowingFragment).
     * @param view View, which should be a FollowingFragment view for which this class is the presenter.
     */
    public FollowingPresenter(View view) {
        this.view = view;
    }

    /**
     * Retrieves the followees of a user.
     * @return  FollowingResponse Object. Contains List<User> followees of a user.
     * @throws IOException
     */
    public FollowingResponse getFollowing() throws IOException {
        FollowingRequest request= new FollowingRequest(view.getUser(), view.getPageSize(), view.getLastFollowee());
        FollowingService followingService = getFollowingService();
        return followingService.getFollowees(request);
    }

    /**
     * Performs a retrieval of a new FollowingService Object.
     * Returns an instance of {@link FollowingService}. For testing purposes, all usages of FollowingService
     * should get their FollowingService instance from this method to allow for mocking of the instance.
     *
     * @return  A new FollowerService Object.
     */
    FollowingService getFollowingService() {
        return new FollowingService();
    }
}

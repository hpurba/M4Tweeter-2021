package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.IFollowingStatusService;

/**
 * FollowingStatusService extends the BaseService Abstract Class to see if the user is Following
 * some other User. (usage is in Loading another user's page which shows if I (the current user)
 * is following "someOtherUser").
 */
public class FollowingStatusService extends BaseService implements IFollowingStatusService {
    // The url_path extension for following a user or unfollowing a user. (Can be found in AWS console -> API:Tweeter -> Stages -> dev tab)
    private static final String URL_PATH_FOLLOW = "/followuser";
    private static final String URL_PATH_UNFOLLOW = "/unfollowuser";

    // FollowingStatus Response and Request Objects.
    FollowingStatusResponse followingStatusResponse;
    FollowingStatusRequest followingStatusRequest;


    /**
     * This is called to get the Following status for the current user over another user.
     * ie. when looking at another user's page, the button shows if the current user is following
     * the currently viewing user's page.
     * Takes a FollowingStatusRequest as the parameter and returns a FollowingStatusResponse.
     *
     * @param request   FollowingStatusRequest Object.
     * @return          FollowingStatusResponse Object.
     * @throws IOException
     */
    public FollowingStatusResponse getFollowingStatus(FollowingStatusRequest request) throws IOException {
        this.followingStatusRequest = request;
        processServiceRequest();
        return followingStatusResponse;
    }

    /**
     * This is the primary method in the Template pattern of the BaseService Abstract Class.
     * This will get the FollowingStatus from the server facade of the current user on another user
     * using the provided FollowingStatusRequest (which is first passed into getFollowingStatus).
     *
     * @throws IOException
     * @throws TweeterRemoteException
     */
    @Override
    public void doServiceSpecificTask() throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();

        // Depending on the user beginning to following another user or un-follow a user, use a specific API endpoint.
        String URL_Extension = "";
        if (followingStatusRequest.getFollowing() == true) {
            this.followingStatusResponse = serverFacade.changeToFollow(followingStatusRequest, URL_PATH_FOLLOW);
        }
        else {
            this.followingStatusResponse = serverFacade.changeToUnFollow(followingStatusRequest, URL_PATH_UNFOLLOW);
        }
    }
}
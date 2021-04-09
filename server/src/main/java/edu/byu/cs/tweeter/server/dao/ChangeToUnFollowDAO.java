package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;

public class ChangeToUnFollowDAO {
    private Boolean followingStatus = false;

    // This actually modifies a following status
    public FollowingStatusResponse changeToUnFollow(FollowingStatusRequest request) {
//        followingStatus = request.getFollowing();
//        if(followingStatus == true) {
//            followingStatus = false;
//        }
        FollowingStatusResponse followingStatusResponse = new FollowingStatusResponse("SomeDude", followingStatus);
        followingStatusResponse.setFollowing(followingStatus);
        return followingStatusResponse;
    }
}

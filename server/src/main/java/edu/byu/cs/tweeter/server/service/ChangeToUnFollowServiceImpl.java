package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.IFollowingStatusService;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
import edu.byu.cs.tweeter.server.dao.ChangeToUnFollowDAO;

public class ChangeToUnFollowServiceImpl implements IFollowingStatusService {

    ChangeToUnFollowDAO changeToUnFollowDAO() { return new ChangeToUnFollowDAO(); }

    @Override
    public FollowingStatusResponse getFollowingStatus(FollowingStatusRequest request) throws IOException, TweeterRemoteException {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getFollowerUser() == null || request.getFolloweeUser() == null || request.getFollowing() == null) {
            throw new RuntimeException("[BadRequest500] 500");
        }
        return changeToUnFollowDAO().changeToUnFollow(request);
    }
}

package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.IFollowingStatusService;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
import edu.byu.cs.tweeter.server.dao.ChangeToFollowDAO;

public class ChangeToFollowServiceImpl implements IFollowingStatusService {

    ChangeToFollowDAO changeToFollowDAO() { return new ChangeToFollowDAO(); }

    @Override
    public FollowingStatusResponse getFollowingStatus(FollowingStatusRequest request) throws IOException, TweeterRemoteException {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getUser() == null && request.getFollowing() == null) {
            throw new RuntimeException("[BadRequest500] 500");
        }
        return changeToFollowDAO().changeToFollow(request);
    }
}

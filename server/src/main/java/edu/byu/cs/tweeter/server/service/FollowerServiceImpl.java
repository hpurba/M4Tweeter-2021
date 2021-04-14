package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.serviceInterfaces.IFollowerService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;

public class FollowerServiceImpl extends ServiceImpl implements IFollowerService {
    public FollowerResponse getFollowers(FollowerRequest request) {
        checkAuthorizationToken(request.getAuthToken());
        return getFollowsDAO().getFollowers(request);
    }

    FollowsDAO getFollowsDAO() { return new FollowsDAO(); }
}

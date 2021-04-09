package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.IFollowerService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.FollowerDAO;

public class FollowerServiceImpl implements IFollowerService {
    public FollowerResponse getFollowers(FollowerRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getLimit() < 0) {
            throw new RuntimeException("[BadRequest500] 500");
        }
        return getFollowerDAO().getFollowers(request);
    }

    FollowerDAO getFollowerDAO() {
        return new FollowerDAO();
    }
}

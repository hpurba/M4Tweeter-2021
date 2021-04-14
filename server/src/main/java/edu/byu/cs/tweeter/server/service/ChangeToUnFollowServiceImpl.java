package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.IFollowingStatusService;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;
import edu.byu.cs.tweeter.server.dao.UsersDAO;

public class ChangeToUnFollowServiceImpl implements IFollowingStatusService {

    FollowsDAO followsDAO() { return new FollowsDAO(); }

    @Override
    public FollowingStatusResponse getFollowingStatus(FollowingStatusRequest request) throws IOException, TweeterRemoteException {
        request.setFollowing(false);
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getUser() == null || request.getOtherUserAlias() == null) {
            throw new RuntimeException("[BadRequest500] 500");
        }
        UsersDAO usersDAO = new UsersDAO();
        User user = usersDAO.getUser(request.getOtherUserAlias());
        return followsDAO().changeToUnFollow(request, user);
    }
}

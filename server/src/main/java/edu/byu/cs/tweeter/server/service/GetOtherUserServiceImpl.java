package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.IGetOtherUserService;
import edu.byu.cs.tweeter.server.dao.UsersDAO;

public class GetOtherUserServiceImpl  extends ServiceImpl implements IGetOtherUserService {

    @Override
    public GetUserResponse getOtherUser(GetUserRequest request) throws IOException {
        checkAuthorizationToken(request.getAuthToken());
        UsersDAO dao = new UsersDAO();
        return dao.getOtherUser(request);
    }
}

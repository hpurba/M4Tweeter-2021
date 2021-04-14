package edu.byu.cs.tweeter.model.service.serviceInterfaces;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;

public interface IGetOtherUserService {
    GetUserResponse getOtherUser(GetUserRequest request) throws IOException;
}

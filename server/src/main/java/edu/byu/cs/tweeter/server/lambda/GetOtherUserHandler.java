package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.server.service.GetOtherUserServiceImpl;

public class GetOtherUserHandler implements RequestHandler<GetUserRequest, GetUserResponse> {
    @Override
    public GetUserResponse handleRequest(GetUserRequest getOtherUserRequest, Context context) {
        if (getOtherUserRequest == null) {
            throw new RuntimeException("[BadRequest400] 400 : logoutRequest is null.");
        } else if (getOtherUserRequest.getAuthToken() == null) {
            throw new RuntimeException("[BadRequest500] 500 : must have an authToken in request. Your AuthToken: " + getOtherUserRequest.getAuthToken());
        }

        GetOtherUserServiceImpl getOtherUserServiceImpl = new GetOtherUserServiceImpl();
        GetUserResponse getUserResponse = null;
        try {
            getUserResponse = getOtherUserServiceImpl.getOtherUser(getOtherUserRequest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("[BadRequest500] 500 : Could not get User" + e.getMessage());
        }
        return getUserResponse;
    }
}
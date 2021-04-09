package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
import edu.byu.cs.tweeter.server.service.ChangeToFollowServiceImpl;

public class ChangeToFollowHandler implements RequestHandler<FollowingStatusRequest, FollowingStatusResponse> {
    @Override
    public FollowingStatusResponse handleRequest(FollowingStatusRequest request, Context context) {
        ChangeToFollowServiceImpl changeToFollowService = new ChangeToFollowServiceImpl();

        FollowingStatusResponse followingStatusResponse = null;
        try {
            followingStatusResponse = changeToFollowService.getFollowingStatus(request);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return followingStatusResponse;
    }
}

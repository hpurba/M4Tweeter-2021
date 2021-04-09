package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;

public interface IFollowingStatusService {
    FollowingStatusResponse getFollowingStatus(FollowingStatusRequest request) throws IOException, TweeterRemoteException;
}

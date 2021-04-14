package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.IGetOtherUserService;

public class GetOtherUserService extends BaseService implements IGetOtherUserService {

    private static final String URL_PATH = "/getotheruser";

    GetUserRequest getUserRequest;
    GetUserResponse getUserResponse;

    @Override
    public void doServiceSpecificTask() throws IOException, TweeterRemoteException {
        getUserResponse = getServerFacade().getOtherUser(getUserRequest, URL_PATH);
        if(getUserResponse.isSuccess()) {
        }
    }

    @Override
    public GetUserResponse getOtherUser(GetUserRequest request) throws IOException {
        this.getUserRequest = request;
        processServiceRequest();
        return getUserResponse;
    }
}

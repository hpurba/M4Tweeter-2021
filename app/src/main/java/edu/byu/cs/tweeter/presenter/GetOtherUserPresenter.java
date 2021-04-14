package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.GetOtherUserService;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;

public class GetOtherUserPresenter {

    private final GetOtherUserPresenter.View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public GetOtherUserPresenter(GetOtherUserPresenter.View view) {
        this.view = view;
    }


    public GetUserResponse getUser(GetUserRequest getUserRequest) throws IOException {
        GetOtherUserService getOtherUserService = new GetOtherUserService();
        return getOtherUserService.getOtherUser(getUserRequest);
    }
}

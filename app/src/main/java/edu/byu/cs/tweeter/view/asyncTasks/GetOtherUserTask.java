package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.presenter.GetOtherUserPresenter;
import edu.byu.cs.tweeter.view.main.following.FollowingFragment;

public class GetOtherUserTask extends AsyncTask<GetUserRequest, Void, GetUserResponse> {

    private GetOtherUserPresenter presenter;
    private GetUserObserver observer;

    private Exception exception;

    public GetOtherUserTask(GetOtherUserPresenter presenter, FollowingFragment followingFragment) {
    }

    public interface GetUserObserver {
        void getUserSuccess(GetUserResponse response);

        void handleException(Exception e);
    }

    public void GetUserTask(GetOtherUserPresenter presenter, GetUserObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetUserResponse doInBackground(GetUserRequest... getUserRequests) {
        GetUserResponse response = null;
        try {
            response = presenter.getUser(getUserRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(GetUserResponse response) {
        if (observer != null) {
            if (exception == null) {
                observer.getUserSuccess(response);
            } else {
                observer.handleException(exception);
            }
        }
    }
}
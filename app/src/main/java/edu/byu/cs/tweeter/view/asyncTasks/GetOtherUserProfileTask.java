package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.OtherUserProfilePresenter;
import edu.byu.cs.tweeter.view.main.follower.FollowersFragment;
import edu.byu.cs.tweeter.view.main.following.FollowingFragment;

public class GetOtherUserProfileTask  extends AsyncTask<FollowingStatusRequest, Void, FollowingStatusResponse> {


    private final OtherUserProfilePresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followingRetrieved(FollowingStatusResponse followingStatusResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve tweets.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetOtherUserProfileTask(OtherUserProfilePresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }
        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve tweets. This method is
     * invoked indirectly by calling {@link #execute(FollowingStatusRequest...)}.
     *
     * @param followingStatusRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected FollowingStatusResponse doInBackground(FollowingStatusRequest... followingStatusRequests) {
        FollowingStatusResponse response = null;
        try {
            response = presenter.getFollowingStatus(followingStatusRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }
        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param followingStatusResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FollowingStatusResponse followingStatusResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.followingRetrieved(followingStatusResponse);
        }
    }
}
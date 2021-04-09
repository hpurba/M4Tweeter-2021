package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
import edu.byu.cs.tweeter.presenter.StoryTweetsPresenter;

public class GetStoryTweetsTask extends AsyncTask<StoryTweetsRequest, Void, StoryTweetsResponse> {
    private final StoryTweetsPresenter presenter;
    private final GetStoryTweetsTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void storyTweetsRetrieved(StoryTweetsResponse storyTweetsResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve tweets.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetStoryTweetsTask(StoryTweetsPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve tweets. This method is
     * invoked indirectly by calling {@link #execute(StoryTweetsRequest...)}.
     *
     * @param storyTweetsRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected StoryTweetsResponse doInBackground(StoryTweetsRequest... storyTweetsRequests) {
        StoryTweetsResponse response = null;
        try {
            response = presenter.getStoryTweets(storyTweetsRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }
        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param storyTweetsResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(StoryTweetsResponse storyTweetsResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.storyTweetsRetrieved(storyTweetsResponse);
        }
    }
}
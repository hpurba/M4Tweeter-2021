package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;
import edu.byu.cs.tweeter.presenter.FeedTweetsPresenter;

/**
 *  GetFeedTweetsTask will call presenter.getFeedTweets();
 */
public class GetFeedTweetsTask extends AsyncTask<FeedTweetsRequest, Void, FeedTweetsResponse> {

    private final FeedTweetsPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void feedTweetsRetrieved(FeedTweetsResponse feedTweetsResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve tweets.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetFeedTweetsTask(FeedTweetsPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }
        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve tweets. This method is
     * invoked indirectly by calling {@link #execute(FeedTweetsRequest...)}.
     *
     * @param feedTweetsRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected FeedTweetsResponse doInBackground(FeedTweetsRequest... feedTweetsRequests) {
        FeedTweetsResponse response = null;
        try {
            response = presenter.getFeedTweets();
        } catch (IOException ex) {
            exception = ex;
        }
        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param feedTweetsResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FeedTweetsResponse feedTweetsResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.feedTweetsRetrieved(feedTweetsResponse);
        }
    }
}

package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;
import edu.byu.cs.tweeter.presenter.TweetPresenter;

public class TweetTask  extends AsyncTask<TweetRequest, Void, TweetResponse> {

    private final TweetPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void tweetSuccessful(TweetResponse tweetResponse);
        void tweetUnsuccessful(TweetResponse tweetResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to tweet.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public TweetTask(TweetPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }



    /**
     * The method that is invoked on a background thread to log the user in. This method is
     * invoked indirectly by calling {@link #execute(TweetRequest...)}.
     *
     * @param tweetRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected TweetResponse doInBackground(TweetRequest... tweetRequests) {
        TweetResponse tweetResponse = null;

        try {
            tweetResponse = presenter.tweet(tweetRequests[0]);

            if(tweetResponse.isSuccess()) {
                //
            }
        } catch (IOException ex) {
            exception = ex;
        }

        return tweetResponse;
    }



    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(TweetRequest...)} method) when the task completes.
     *
     * @param tweetResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(TweetResponse tweetResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(tweetResponse.isSuccess()) {
            observer.tweetSuccessful(tweetResponse);
        } else {
            observer.tweetUnsuccessful(tweetResponse);
        }
    }


}

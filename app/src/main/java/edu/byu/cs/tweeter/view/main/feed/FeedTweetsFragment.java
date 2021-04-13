package edu.byu.cs.tweeter.view.main.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;
import edu.byu.cs.tweeter.presenter.FeedTweetsPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFeedTweetsTask;
import edu.byu.cs.tweeter.view.main.OtherUserProfile.OtherUserProfileActivity;

public class FeedTweetsFragment extends Fragment implements FeedTweetsPresenter.View {

    private static final String LOG_TAG = "FeedTweetsFragment";
    private static User CURRENT_USER_KEY;
    private static  String AUTH_TOKEN_KEY;
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private static final int PAGE_SIZE = 8; // Number of items for each scrolling pagination.

    private User user;
    private String authToken;
    private Tweet tweet;
    private FeedTweetsPresenter presenter;

    private FeedTweetsFragment.FeedTweetsRecyclerViewAdapter feedTweetsRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @return the fragment.
     */
    public static FeedTweetsFragment newInstance(User user, String authToken) {
        FeedTweetsFragment fragment = new FeedTweetsFragment();
        Bundle args = new Bundle(2);
//        this.user = user;
//        this.authToken = authToken;
        CURRENT_USER_KEY = user;
//        args.putSerializable(CURRENT_USER_KEY, user);
//        args.putSerializable(AUTH_TOKEN_KEY, authToken);
        AUTH_TOKEN_KEY = authToken;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedtweets, container, false);

        //noinspection ConstantConditions
//        user = (User) getArguments().getSerializable(CURRENT_USER_KEY);
//        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);


        presenter = new FeedTweetsPresenter(this);

        RecyclerView feedTweetsRecyclerView = view.findViewById(R.id.feedTweetsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedTweetsRecyclerView.setLayoutManager(layoutManager);

        feedTweetsRecyclerViewAdapter = new FeedTweetsFragment.FeedTweetsRecyclerViewAdapter();
        feedTweetsRecyclerView.setAdapter(feedTweetsRecyclerViewAdapter);

        feedTweetsRecyclerView.addOnScrollListener(new FeedTweetsFragment.FeedTweetsRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    @Override
    public User getUser() {
        return CURRENT_USER_KEY;
    }

    @Override
    public int getPageSize() {
        return PAGE_SIZE;
    }

    @Override
    public Tweet getLastTweet() {
        return feedTweetsRecyclerViewAdapter.lastTweet;
    }

    @Override
    public String getAuthToken() {
        return AUTH_TOKEN_KEY;
    }

    /**
     * The ViewHolder for the RecyclerView that displays the feed tweet data.
     */
    private class FeedTweetsHolder extends RecyclerView.ViewHolder {

        private ImageView userImage;
        private TextView userName;
        private final TextView userAlias;
        private final TextView tweetText;
        private final Context context;

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        FeedTweetsHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            context = itemView.getContext();

            if(viewType == ITEM_VIEW) {
                userAlias = itemView.findViewById(R.id.userAliasForTweet);
                tweetText = itemView.findViewById(R.id.tweetTextInRow);

                //
//                userImage = itemView.findViewById(R.id.userImage);
                userName = itemView.findViewById(R.id.userName);
                //

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "You selected '" + userAlias.getText() + "'.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, OtherUserProfileActivity.class);
                        intent.putExtra(OtherUserProfileActivity.CURRENT_USER_KEY, user);
                        intent.putExtra(OtherUserProfileActivity.AUTH_TOKEN_KEY, authToken);
                        intent.putExtra(OtherUserProfileActivity.OTHER_USER_ALIAS, userAlias.getText().toString());
//                        intent.putExtra(OtherUserProfileActivity.OTHER_USER_FULL_NAME, userName.getText().toString());
                        intent.putExtra(OtherUserProfileActivity.OTHER_USER_FULL_NAME, user.getFirstName() +  " " + user.getLastName());

                        context.startActivity(intent);
                    }
                });
            } else {
                userAlias = null;
                tweetText = null;
            }
        }

        /**
         * Binds the user's data to the view.
         *
         * @param tweet the tweet.
         */
        void bindTweet(Tweet tweet) {
            if(tweet != null) {
                userAlias.setText(tweet.getAlias());
                tweetText.setText(tweet.getTweetText());
            }
//            userName.setText(tweet.getUserName());
        }
    }

    /**
     * The adapter for the RecyclerView that displays the feed tweet data.
     */
    private class FeedTweetsRecyclerViewAdapter extends RecyclerView.Adapter<FeedTweetsFragment.FeedTweetsHolder> implements GetFeedTweetsTask.Observer {

        private final List<Tweet> tweets = new ArrayList<>();
        private Tweet lastTweet;

        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of feed tweet data.
         */
        FeedTweetsRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new tweets to the list from which the RecyclerView retrieves the tweets it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newTweets the users to add.
         */
        void addItems(List<Tweet> newTweets) {
            int startInsertPosition = tweets.size();
            tweets.addAll(newTweets);
            this.notifyItemRangeInserted(startInsertPosition, newTweets.size());
        }

        /**
         * Adds a single tweets to the list from which the RecyclerView retrieves the tweets it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param tweet the user to add.
         */
        void addItem(Tweet tweet) {
            tweets.add(tweet);
            this.notifyItemInserted(tweets.size() - 1);
        }

        /**
         * Removes a tweet from the list from which the RecyclerView retrieves the tweets it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param tweet the tweet to remove.
         */
        void removeItem(Tweet tweet) {
            int position = tweets.indexOf(tweet);
            tweets.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for a tweet to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         *
         * @param parent the parent view.
         * @param viewType the type of the view (ignored in the current implementation).
         * @return the view holder.
         */
        @NonNull
        @Override
        public FeedTweetsFragment.FeedTweetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedTweetsFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.tweet_row, parent, false);
            }

            return new FeedTweetsFragment.FeedTweetsHolder(view, viewType);
        }

        /**
         * Binds the tweet at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param feedTweetsHolder the ViewHolder to which the tweet should be bound.
         * @param position the position (in the list of tweets) that contains the tweet to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull FeedTweetsFragment.FeedTweetsHolder feedTweetsHolder, int position) {
            if(!isLoading) {
                feedTweetsHolder.bindTweet(tweets.get(position));
            }
        }

        /**
         * Returns the current number of tweets available for display.
         * @return the number of tweets available for display.
         */
        @Override
        public int getItemCount() {
            return tweets.size();
        }

        /**
         * Returns the type of the view that should be displayed for the item currently at the
         * specified position.
         *
         * @param position the position of the items whose view type is to be returned.
         * @return the view type.
         */
        @Override
        public int getItemViewType(int position) {
            return (position == tweets.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more feed tweet
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFeedTweetsTask getFeedTweetsTask = new GetFeedTweetsTask(presenter, this);
            getFeedTweetsTask.execute();
        }

        /**
         * A callback indicating more tweet data has been received. Loads the new tweets
         * and removes the loading footer.
         *
         * @param feedTweetsResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void feedTweetsRetrieved(FeedTweetsResponse feedTweetsResponse) {
            if(feedTweetsResponse != null && feedTweetsResponse.getTweets().size() != 0) {
                List<Tweet> tweets = feedTweetsResponse.getTweets();

                lastTweet = (tweets.size() > 0) ? tweets.get(tweets.size() -1) : null;
                hasMorePages = feedTweetsResponse.getHasMorePages();

                isLoading = false;
                removeLoadingFooter();
                feedTweetsRecyclerViewAdapter.addItems(tweets);
            }
            else {
                isLoading = false;
                removeLoadingFooter();
            }
        }

        /**
         * A callback indicating that an exception was thrown by the presenter.
         *
         * @param exception the exception.
         */
        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * Adds a dummy user to the list of users so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            User tempUser = new User("FirstName", "LastName", "@DummyUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            addItem(new Tweet(tempUser, "Tweet Text Goes Here", 0));
        }

        /**
         * Removes the dummy user from the list of users so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(tweets.get(tweets.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class FeedTweetsRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        FeedTweetsRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!feedTweetsRecyclerViewAdapter.isLoading && feedTweetsRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    feedTweetsRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}

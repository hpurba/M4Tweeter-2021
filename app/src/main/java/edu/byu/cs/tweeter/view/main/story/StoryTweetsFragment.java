package edu.byu.cs.tweeter.view.main.story;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
import edu.byu.cs.tweeter.presenter.StoryTweetsPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetStoryTweetsTask;

public class StoryTweetsFragment extends Fragment implements StoryTweetsPresenter.View {

    private static final String LOG_TAG = "StoryTweetsFragment";
    private static User CURRENT_USER_KEY;
    private static String AUTH_TOKEN_KEY;
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private static final int PAGE_SIZE = 9;

    private Tweet tweet;
    private StoryTweetsPresenter presenter;
    private User user;
    private String authToken;
    private StoryTweetsFragment.StoryTweetsRecyclerViewAdapter storyTweetsRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @return the fragment.
     */
    public static StoryTweetsFragment newInstance(User user, String authToken) {
        StoryTweetsFragment fragment = new StoryTweetsFragment();
        Bundle args = new Bundle(2);
//        args.putSerializable(CURRENT_USER_KEY, user);
//        args.putSerializable(AUTH_TOKEN_KEY, authToken);
        CURRENT_USER_KEY = user;
        AUTH_TOKEN_KEY = authToken;

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storytweets, container, false);

        //noinspection ConstantConditions
//        user = (User) getArguments().getSerializable("CURRENT_USER_KEY");
//        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new StoryTweetsPresenter(this);

        RecyclerView storyTweetsRecyclerView = view.findViewById(R.id.storyTweetsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        storyTweetsRecyclerView.setLayoutManager(layoutManager);

        storyTweetsRecyclerViewAdapter = new StoryTweetsFragment.StoryTweetsRecyclerViewAdapter();
        storyTweetsRecyclerView.setAdapter(storyTweetsRecyclerViewAdapter);

        storyTweetsRecyclerView.addOnScrollListener(new StoryTweetsFragment.StoryTweetsRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    @Override
    public String getAuthToken() {
        return AUTH_TOKEN_KEY;
    }

    /**
     * The ViewHolder for the RecyclerView that displays the story tweet data.
     */
    private class StoryTweetsHolder extends RecyclerView.ViewHolder {
        private final TextView userAlias;
        private final TextView tweetText;
        private final TextView userFullName;
        private final ImageView userImage;
        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        StoryTweetsHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                userAlias = itemView.findViewById(R.id.userAliasForTweet);
                userFullName = itemView.findViewById(R.id.NameOfUser);
                tweetText = itemView.findViewById(R.id.tweetTextInRow);
                userImage = itemView.findViewById(R.id.userImageForTweet);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "You selected '" + userAlias.getText() + "'.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                userAlias = null;
                userFullName = null;
                tweetText = null;
                userImage = null;
            }
        }
        /**
         * Binds the user's data to the individual tweet view (The one in a row).
         *
         * @param tweet the tweet.
         */
        void bindTweet(Tweet tweet) {
            userAlias.setText(tweet.getAlias());
            userFullName.setText(tweet.getUser().getFirstName() + " " + tweet.getUser().getLastName());
            tweetText.setText(tweet.getTweetText());
            Bitmap tweetUserProfileImageBitmap = BitmapFactory.decodeByteArray(tweet.getUser().getImageBytes(), 0, tweet.getUser().getImageBytes().length);
            userImage.setImageBitmap(tweetUserProfileImageBitmap);
        }
    }

    /**
     * The adapter for the RecyclerView that displays the story tweet data.
     */
    private class StoryTweetsRecyclerViewAdapter extends RecyclerView.Adapter<StoryTweetsFragment.StoryTweetsHolder> implements GetStoryTweetsTask.Observer {

        private final List<Tweet> tweets = new ArrayList<>();
        private edu.byu.cs.tweeter.model.domain.Tweet lastTweet;
        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of story tweet data.
         */
        StoryTweetsRecyclerViewAdapter() {
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
        public StoryTweetsFragment.StoryTweetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StoryTweetsFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.tweet_row, parent, false);
            }

            return new StoryTweetsFragment.StoryTweetsHolder(view, viewType);
        }

        /**
         * Binds the tweet at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param storyTweetsHolder the ViewHolder to which the tweet should be bound.
         * @param position the position (in the list of tweets) that contains the tweet to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull StoryTweetsFragment.StoryTweetsHolder storyTweetsHolder, int position) {
            if(!isLoading) {
                storyTweetsHolder.bindTweet(tweets.get(position));
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
         * Causes the Adapter to display a loading footer and make a request to get more story tweet
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetStoryTweetsTask getStoryTweetsTask = new GetStoryTweetsTask(presenter, this);

            String authToken = AUTH_TOKEN_KEY;
            authToken = getAuthToken();
            User user = CURRENT_USER_KEY;
            user = getUser();
            StoryTweetsRequest request = new StoryTweetsRequest(user, PAGE_SIZE, lastTweet, authToken);

            getStoryTweetsTask.execute(request);
        }

        /**
         * A callback indicating more tweet data has been received. Loads the new tweets
         * and removes the loading footer.
         *
         * @param storyTweetsResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void storyTweetsRetrieved(StoryTweetsResponse storyTweetsResponse) {
            if (storyTweetsResponse != null && storyTweetsResponse.getTweets().size() != 0) {
                List<Tweet> tweets = storyTweetsResponse.getTweets();

                lastTweet = (tweets.size() > 0) ? tweets.get(tweets.size() -1) : null;
                hasMorePages = storyTweetsResponse.getHasMorePages();

                isLoading = false;
                removeLoadingFooter();
                storyTweetsRecyclerViewAdapter.addItems(tweets);
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

    private User getUser() {
        return CURRENT_USER_KEY;
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class StoryTweetsRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        StoryTweetsRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
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

            if (!storyTweetsRecyclerViewAdapter.isLoading && storyTweetsRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    storyTweetsRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }


}

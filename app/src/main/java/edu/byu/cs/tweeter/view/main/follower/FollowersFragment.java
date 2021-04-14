package edu.byu.cs.tweeter.view.main.follower;

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
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.OtherUserProfilePresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowersTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetOtherUserProfileTask;
import edu.byu.cs.tweeter.view.main.OtherUserProfile.OtherUserProfileActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class FollowersFragment extends Fragment implements FollowerPresenter.View, OtherUserProfilePresenter.View, GetOtherUserProfileTask.Observer {

    private static final String LOG_TAG = "FollowersFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 9;

    private User user;
    private String authToken;
    private FollowerPresenter presenter;
    private FollowersFragment followersFragment;
    private OtherUserProfilePresenter presenterNext;
    private View rootView;
    private User lastFollower;

    private FollowersFragment.FollowerRecyclerViewAdapter followerRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static FollowersFragment newInstance(User user, String authToken) {
        FollowersFragment fragment = new FollowersFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
//        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        rootView = this.getView();

        presenter = new FollowerPresenter(this);
        presenterNext = new OtherUserProfilePresenter(this);

        RecyclerView followerRecyclerView = view.findViewById(R.id.followerRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followerRecyclerView.setLayoutManager(layoutManager);

        followerRecyclerViewAdapter = new FollowersFragment.FollowerRecyclerViewAdapter();
        followerRecyclerView.setAdapter(followerRecyclerViewAdapter);

        followerRecyclerView.addOnScrollListener(new FollowersFragment.FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    @Override
    public void followingRetrieved(FollowingStatusResponse followingStatusResponse) {

        // need to get current activity
//        getActivity().getBaseContext()
//        rootView.getContext()

//        Intent intent = new Intent(getActivity().getBaseContext(), OtherUserProfileActivity.class);
//
////        intent.putExtra(MainActivity.CURRENT_USER_KEY, followingStatusResponse.getUser());
////        intent.putExtra(MainActivity.FOLLOWING_BOOL, followingStatusResponse.getFollowing());
//
//        startActivity(intent);
    }

    @Override
    public void handleException(Exception exception) {

    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public int getPageSize() {
        return PAGE_SIZE;
    }

    @Override
    public User getLastFollower() {
        return lastFollower;
    }

    /**
     * The ViewHolder for the RecyclerView that displays the Following data.
     */
    private class FollowerHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final Context context;

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        FollowerHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            context = itemView.getContext();

            if(viewType == ITEM_VIEW) {
                userImage = itemView.findViewById(R.id.userImage);
                userAlias = itemView.findViewById(R.id.userAlias);
                userName = itemView.findViewById(R.id.userName);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(context, OtherUserProfileActivity.class);
                        intent.putExtra(OtherUserProfileActivity.CURRENT_USER_KEY, user);
                        intent.putExtra(OtherUserProfileActivity.AUTH_TOKEN_KEY, authToken);
                        intent.putExtra(OtherUserProfileActivity.OTHER_USER_ALIAS, userAlias.getText().toString());
                        intent.putExtra(OtherUserProfileActivity.OTHER_USER_FULL_NAME, userName.getText().toString());

                        context.startActivity(intent);
                    }
                });
            } else {
                userImage = null;
                userAlias = null;
                userName = null;
            }
        }

        /**
         * Binds the user's data to the view.
         *
         * @param user the user.
         */
        void bindUser(User user) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
        }
    }

    /**
     * The adapter for the RecyclerView that displays the Following data.
     */
    private class FollowerRecyclerViewAdapter extends RecyclerView.Adapter<FollowersFragment.FollowerHolder> implements GetFollowersTask.Observer {

        private final List<User> users = new ArrayList<>();

//        private User lastFollower;


        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of following data.
         */
        FollowerRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new users to the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newUsers the users to add.
         */
        void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        /**
         * Adds a single user to the list from which the RecyclerView retrieves the users it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param user the user to add.
         */
        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        /**
         * Removes a user from the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param user the user to remove.
         */
        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for a follower to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         *
         * @param parent the parent view.
         * @param viewType the type of the view (ignored in the current implementation).
         * @return the view holder.
         */
        @NonNull
        @Override
        public FollowersFragment.FollowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowersFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }

            return new FollowersFragment.FollowerHolder(view, viewType);
        }

        /**
         * Binds the follower at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param followerHolder the ViewHolder to which the follower should be bound.
         * @param position the position (in the list of followers) that contains the follower to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull FollowersFragment.FollowerHolder followerHolder, int position) {
            if(!isLoading) {
                followerHolder.bindUser(users.get(position));
            }
        }

        /**
         * Returns the current number of followeers available for display.
         * @return the number of followers available for display.
         */
        @Override
        public int getItemCount() {
            return users.size();
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
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more following
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();
            GetFollowersTask getFollowersTask = new GetFollowersTask(presenter, this);
            getFollowersTask.execute();
        }

        /**
         * A callback indicating more following data has been received. Loads the new followers
         * and removes the loading footer.
         *
         * @param followerResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void followersRetrieved(FollowerResponse followerResponse) {
            List<User> followers = followerResponse.getFollowers();

            lastFollower = (followers.size() > 0) ? followers.get(followers.size() -1) : null;
            hasMorePages = followerResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            followerRecyclerViewAdapter.addItems(followers);
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
            addItem(new User("Dummy", "User", ""));
        }

        /**
         * Removes the dummy user from the list of users so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
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

            if (!followerRecyclerViewAdapter.isLoading && followerRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    followerRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }


}

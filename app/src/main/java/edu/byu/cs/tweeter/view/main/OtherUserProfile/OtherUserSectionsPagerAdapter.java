package edu.byu.cs.tweeter.view.main.OtherUserProfile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.main.follower.FollowersFragment;
import edu.byu.cs.tweeter.view.main.following.FollowingFragment;
import edu.byu.cs.tweeter.view.main.story.StoryTweetsFragment;

public class OtherUserSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;
    private final User user;
    private final User otherUser;
    private final String authToken;

    public OtherUserSectionsPagerAdapter(Context context, FragmentManager fm, User user, User otherUser, String authToken) {
        super(fm);
        mContext = context;
        this.user = user;
        this.otherUser = otherUser;
        this.authToken = authToken;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StoryTweetsFragment().newInstance(user, authToken); // STORY
            case 1:
                return FollowingFragment.newInstance(user, otherUser, authToken);
            case 2:
                return FollowersFragment.newInstance(user,otherUser, authToken);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}

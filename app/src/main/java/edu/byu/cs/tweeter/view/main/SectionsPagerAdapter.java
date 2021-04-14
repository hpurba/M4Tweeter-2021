package edu.byu.cs.tweeter.view.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.main.feed.FeedTweetsFragment;
import edu.byu.cs.tweeter.view.main.follower.FollowersFragment;
import edu.byu.cs.tweeter.view.main.following.FollowingFragment;
import edu.byu.cs.tweeter.view.main.story.StoryTweetsFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the sections/tabs/pages
 * of the Main Activity.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {

//    private static final int FOLLOWING_FRAGMENT_POSITION = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.feedTabTitle, R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;
    private final User user;
    private User otherUser;
    private final String authToken;

    public SectionsPagerAdapter(Context context, FragmentManager fm, User user, User otherUser, String authToken) {
        super(fm);
        mContext = context;
        this.user = user;
        this.otherUser = otherUser;
        this.authToken = authToken;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FeedTweetsFragment().newInstance(user, authToken); // FEED
            case 1:
                return new StoryTweetsFragment().newInstance(user, authToken); // STORY
            case 2:
                return FollowingFragment.newInstance(user,otherUser, authToken);
            case 3:
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
        // Show 4 total pages.
        return 4;
    }
}
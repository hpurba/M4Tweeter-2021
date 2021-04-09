package edu.byu.cs.tweeter.view.main.LoginRegister;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * This LoginRegisterSectionsPagerAdapter extends the FragmentPagerAdapter which
 * is useful when there are a handful of typically more static fragments to be
 * paged through, such as a set of tabs. In this case it is the
 * 0 - LoginFragment
 * 1 - RegisterFragment
 */
class LoginRegisterSectionsPagerAdapter extends FragmentPagerAdapter {
    // TO DO: FragmentPagerAdapter is depreciated, consider fixing this later.

    public LoginRegisterSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem returns the correct Fragment corresponding to the position of the tab buttons.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
            default:
                return null;
        }
    }

    /**
     * Returns the number of Fragments available to cycle through.
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }
}
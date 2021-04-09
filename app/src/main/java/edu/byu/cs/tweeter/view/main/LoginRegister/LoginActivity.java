package edu.byu.cs.tweeter.view.main.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.LoginPresenter;

/**
 * tweeter Login and Register page.
 * This activity contains the application name at the top "tweeter".
 * The bottom two thirds contains the tab layout with the PagerAdapter.
 * In the PagerAdapter, each page is represented by its own Fragment.
 * In this case it would be the LOGIN on the left and the REGISTER on the right.
 *
 * TODO: Currently, Login will log in a TestUser, ignoring any @username and password combination.
 *  Fix this in later milestones when appropriate.
 */
public class LoginActivity extends AppCompatActivity  {
    private static final String LOG_TAG = "LoginActivity";

    // Tabs and ViewPager
    TabLayout tabLayout;    // Button Tabs
    ViewPager myViewPager;  // Widget that allows the user to swipe left or right to see an entirely new screen.
    LoginRegisterSectionsPagerAdapter myLoginRegisterSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SetContentView is used to fill the window with the UI provided from layout file.
        // In this case of setContentView(R.layout.activity_login).
        // activity_login.xml is used which includes "tweeter" header and the tabs/PagerAdapter
        setContentView(R.layout.activity_login);

        myViewPager = (ViewPager) findViewById(R.id.login_register_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        setPagerAdapter();  // sets up the PagerAdapter
        setTabLayout(); // tabLayout are the buttons: "LOGIN" and "REGISTER"
    }

    /**
     * Sets up the Tabs.
     * Left (0) being "LOGIN"
     * Right (1) being "REGISTER"
     */
    private void setTabLayout() {
        tabLayout.setupWithViewPager(myViewPager);
        tabLayout.getTabAt(0).setText("LOGIN");
        tabLayout.getTabAt(1).setText("REGISTER");
    }

    /**
     * Create a new LoginRegisterSectionsPagerAdapter which extends FragmentPagerAdapter
     * myLoginRegisterSectionsPagerAdapter is set up to hold the Login fragment and the Register fragment
     * Assign "myViewPager" to the PagerAdapter with the fragments.
     */
    private void setPagerAdapter(){
        myLoginRegisterSectionsPagerAdapter = new LoginRegisterSectionsPagerAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myLoginRegisterSectionsPagerAdapter);
    }
}

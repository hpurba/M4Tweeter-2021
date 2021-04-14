package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.TweetPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.asyncTasks.TweetTask;
import edu.byu.cs.tweeter.view.main.LoginRegister.LoginActivity;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements LogoutPresenter.View, LogoutTask.Observer, TweetPresenter.View, TweetTask.Observer {

    public static User CURRENT_USER_KEY;
    public static String AUTH_TOKEN_KEY;
//    public static final String FOLLOWING_BOOL = "followingBool";

    private LogoutPresenter logoutPresenter;
    private TweetPresenter tweetPresenter;
    private Toast logoutToast;
    private Toast tweetCompleteToast;
    public User user;
    public ViewGroup root;
    public byte[] savedImageBytes;

    private String firstName;
    private String lastName;
    private String alias;
    private int followerNumCount;
    private int followingNumCount;
    private String tweetText;
    private View view;
    private PopupWindow popupWindow;
    private ImageView tweetProfilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(android.R.id.content).getRootView();

        logoutPresenter = new LogoutPresenter(this); // maybe change this to view
        tweetPresenter = new TweetPresenter(this);

        user = (User) getIntent().getSerializableExtra("CURRENT_USER_KEY");
        user = getUser();

        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }
        else {
            // grab the user's
            firstName = user.getFirstName();
            lastName = user.getLastName();
            alias =  user.getAlias();
            followerNumCount = user.getFollowersCount();
            followingNumCount = user.getFollowingCount();
        }
//        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, null, getAuthToken());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        // OPEN UP THE WRITE TWEET BOX!
        FloatingActionButton fab = findViewById(R.id.fab);
        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        // THIS SHOULD GO TO MAKING A NEW TWEET FUNCTIONALITY
//        ViewGroup view = (ViewGroup) getWindow().getDecorView().getRootView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
                onButtonShowPopupWindowClick(view, root);
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        // Set the User Alias
        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        // Sets the logged in user's profile Image
        ImageView userImageView = findViewById(R.id.userImageMain);
        byte [] imageBytes = user.getImageBytes();    // old method
        if(imageBytes != null) {
            setImageViewWithByteArray(userImageView, imageBytes);
            saveImageBytes(imageBytes);
        }

        // Display the Followee Count
        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, followingNumCount));

        // Display the Follower Count
        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, followerNumCount));
    }

    public void saveImageBytes(byte[] imageBytes) {
        this.savedImageBytes = imageBytes;
    }

    public byte[] getSavedImageBytes() {
        return savedImageBytes;
    }

    public String getAuthToken() {
        return (String) getIntent().getSerializableExtra("AUTH_TOKEN_KEY");
    }

    public User getUser() {
        return (User) getIntent().getSerializableExtra("CURRENT_USER_KEY");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public static void setImageViewWithByteArray(ImageView view, byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        view.setImageBitmap(bitmap);
    }

    public static byte[] fetchRemoteFile(String location) throws Exception {
        URL url = new URL(location);
        InputStream is = null;
        byte[] bytes = null;
        try {
            is = url.openStream ();
            bytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            //handle errors
        }
        finally {
            if (is != null) is.close();
        }
        return bytes;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logoutMenu:
                logoutOfApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Logout of app is requested by the press of the logout button
     * A toast is sent to the user that he/she is logging out.
     * Logout Task is performed.
     */
    public void logoutOfApp(){
        // Display Toast to user
        logoutToast = Toast.makeText(this, "Logging Out!", Toast.LENGTH_LONG);
        logoutToast.show();

        // Execute the logout
        LogoutTask logoutTask = new LogoutTask(logoutPresenter, this);
        logoutTask.execute();
    }


    @Override
    public void logoutSuccessful(LogoutResponse logoutResponse) {
        user = null;
        AUTH_TOKEN_KEY = null;
        Intent intent = new Intent(this, LoginActivity.class);
        logoutToast.cancel();
        startActivity(intent);
    }

    @Override
    public void logoutUnsuccessful(LogoutResponse logoutResponse) {
        // TODO: Write code to handle this case.
    }

    @Override
    public void tweetSuccessful(TweetResponse tweetResponse) {
        // TODO: Write code to handle this case.
        String output = "Nice job " + firstName + " " + lastName + "! You just posted a tweet as " + alias + ".\n Your tweet: " + tweetText;
        tweetCompleteToast = Toast.makeText(this, output, Toast.LENGTH_LONG);
        tweetCompleteToast.show();
    }

    @Override
    public void tweetUnsuccessful(TweetResponse tweetResponse) {
        // TODO: Write code to handle this case.
        String output = "Uh oh! " + tweetResponse.getMessage();
        tweetCompleteToast = Toast.makeText(this, output, Toast.LENGTH_LONG);
        tweetCompleteToast.show();
    }

    @Override
    public void handleException(Exception ex) {
        // TODO: Write code to handle this case.
        String output = "Oh my! " + ex.toString();
        tweetCompleteToast = Toast.makeText(this, output, Toast.LENGTH_LONG);
        tweetCompleteToast.show();
    }

    /**
     * This handles when the button to post a tweet is pressed.
     * A popup window is shown and applies the dimming of the main activity.
     * @param view
     * @param root
     */
    public void onButtonShowPopupWindowClick(View view, ViewGroup root) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment_tweet, null);   // root was null

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        applyDim(root, 0.6f);


        // set the First name last name, alias, and the user profile picture.
        TextView userName = popupView.findViewById(R.id.userFirstNameLastNameInTweetWindow);
        userName.setText(user.getName());

        TextView userAlias = popupView.findViewById(R.id.userAliasInTweetWindow);
        userAlias.setText(user.getAlias());

        // Verify that the image bytes are in the user object, if not retrieve it from the url.
        if (user.getImageBytes() == null) {
            user.setImageBytes(getSavedImageBytes());
        }


        tweetProfilePictureView = popupView.findViewById(R.id.tweetProfilePictureImageView);
        Bitmap userProfilePictureBitmap = BitmapFactory.decodeByteArray(user.getImageBytes(), 0, user.getImageBytes().length);
        tweetProfilePictureView.setImageBitmap(userProfilePictureBitmap);

        // Clicking away throws away the tweet
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                clearDim(root);
            }
        });

        Button tweetButton = popupView.findViewById(R.id.et_postTweetButton);
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tweetEditText =  popupView.findViewById(R.id.et_tweetText);
                tweetText = tweetEditText.getText().toString();
                if (postTweet(tweetText) == true) {
                   // continue
                }
                else {
                    // You have a problem!!
                }
                popupWindow.dismiss();
                clearDim(root);
            }
        });
    }

    /**
     * This will post the tweet with the tweet text provided.
     * It will also display a toast indicating the post has been made.
     * @param tweetText
     * @return
     */
    public Boolean postTweet(String tweetText){
//        String output = "Nice job " + firstName + " " + lastName + "! You just posted a tweet as " + alias + ".\n Your tweet: " + tweetText;
//        tweetCompleteToast = Toast.makeText(this, output, Toast.LENGTH_LONG);
//        tweetCompleteToast.show();

        long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        Tweet tweet = new Tweet(user, tweetText, currentTime);

        String authToken = getAuthToken();
        TweetRequest tweetRequest = new TweetRequest(tweet, authToken);
        tweetRequest.setAuthToken(authToken);

        TweetTask tweetTask = new TweetTask(tweetPresenter, this);
        tweetTask.execute(tweetRequest);
        return true;
    }

    /**
     * This handles the background dimming of the main activity while the
     * make a tweet window is up.
     * Here is a link to where more information on how the code works can be found:
     * https://stackoverflow.com/questions/3221488/blur-or-dim-background-when-android-popupwindow-active
     *
     * @param parent
     * @param dimAmount
     */
    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    /**
     * This handles the background un-dimming of the main activity once the
     * tweet has been made and the tweet posting window has been dismissed.
     *
     * @param parent
     */
    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    @Override
    public String getUserAlias() {
        return user.getAlias();
    }



}
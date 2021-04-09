package edu.byu.cs.tweeter.view.main.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

/**
 * LoginFragment is the View which displays the content for logging a user in.
 * Implements Async LoginTask and LoginPresenter for calling the login() method.
 */
public class LoginFragment extends Fragment implements LoginPresenter.View, LoginTask.Observer {
    public LoginFragment() {}

    // Used in Logging. Specifically when an exception occurs
    private static final String LOG_TAG = "LoginFragment";

    // Presenter assumes the functionality of the “middle-man” all presentation logic is diverted here
    private LoginPresenter presenter;

    // Toast used to notify the User of a Login (Successful, Unsuccessful, or Exception)
    private Toast loginInToast;

    // ExitText for the user to enter their username and password.
    private EditText usernameEditText;  // @username
    private EditText passwordEditText;  // password

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Assigns the LoginPresenter to this view (LoginFragment).
        presenter = new LoginPresenter(this);

        // Assigns the fields in the fragment_login.xml to the associated EditText fields.
        usernameEditText = view.findViewById(R.id.et_username1);
        passwordEditText = view.findViewById(R.id.et_password1);

        // Assigns the LoginButton in the xml file as a Button called loginButton.
        Button loginButton = view.findViewById(R.id.LoginButton);

        // When the loginButton is clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Makes a login request. The user is hard-coded, so it doesn't matter what data we put
             * into the LoginRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                // Show the Toast
                loginInToast = Toast.makeText(getActivity(), "Logging In", Toast.LENGTH_LONG);
                loginInToast.show();
                // This is wasteful but for demonstrative purposes. Use the above for the toast.
                //  presenter.showLoggingInToast();

                // Make the Login
                LoginTask loginTask = new LoginTask(presenter, LoginFragment.this);
                loginTask.execute();
            }
        });
        return view;
    }


    /**
     *  The callback method that gets invoked for a successful login. Displays the MainActivity.
     *   @param loginResponse the response from the login request.
     *
     */
    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, loginResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, loginResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(getActivity(), "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(getActivity(), "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * Retrieves the username from the view's username input text field.
     * @return username as a String.
     */
    @Override
    public String getUsernameText() {
        String username = usernameEditText.getText().toString();
        return username;
    }

    /**
     * Retrieves the password from the view's username input text field.
     * @return password as a String.
     */
    @Override
    public String getPasswordText() {
        String password = passwordEditText.getText().toString();
        return password;
    }

    /**
     * This is for demonstrative purposes only.
     * Demonstrates how view will call presenter for a method which involves logic.
     * Then the presenter may call this method to update/modify the view.
     */
//    @Override
//    public void showLoggingInToast() {
//        // Show the Toast
//        loginInToast = Toast.makeText(getActivity(), "Logging In", Toast.LENGTH_LONG);
//        loginInToast.show();
//    }
}

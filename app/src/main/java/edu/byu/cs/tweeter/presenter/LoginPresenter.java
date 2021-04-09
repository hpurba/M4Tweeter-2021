package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter {

    private final View view;

    /**
     * This is the interface for the presenter's view (LoginFragment).
     * It is the interface by which this presenter can communicate with it's view (call it's methods).
     * Methods listed here should be used only for retrieval, or raising an event (ex: change a button status).
     *  - If needed, specify methods here that will be called on the view in response to model updates.
     */
    public interface View {
        String getUsernameText();
        String getPasswordText();

        // Demonstrative purposes only
        // void showLoggingInToast();
        // TODO: Not necessary, just an idea: Activate the LoginButton (if both text fields are filled this method is called on the view.)
    }

    /**
     * Creates an instance of a LoginPresenter with the provided view (should be a LoginFragment).
     * @param view View, which should be a LoginFragment view for which this class is the presenter for.
     */
    public LoginPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a login request.
     * @return  LoginResponse Object.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public LoginResponse login() throws IOException, TweeterRemoteException {
        // Construct a Login Request using the username and password in the text fields.
        LoginRequest loginRequest = new LoginRequest(view.getUsernameText(), view.getPasswordText());

        LoginService loginService = getLoginService();
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null){
            throw new IOException();
        }
        return loginService.login(loginRequest);
    }

    /**
     * Performs a retrieval of a new LoginService
     * @return  A new LoginService Object.
     */
    public LoginService getLoginService() {
        return new LoginService();
    }

    // Demonstrative purposes only for calling a presenter method from the view (Login Fragment)
    //    public void showLoggingInToast() {
    //        view.showLoggingInToast();
    //    }
}

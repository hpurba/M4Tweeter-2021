package edu.byu.cs.tweeter.model.service;

import java.io.IOException;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

/**
 * LoginService extends the BaseService Abstract Class to login the user.
 */
public class LoginService extends BaseService implements ILoginService {
    // The url_path extension for login. (Can be found in AWS console -> API:Tweeter -> Stages -> dev tab)
    private static final String URL_PATH = "/loginuser";

    // Login Request and Response Objects.
    LoginRequest loginRequest;
    LoginResponse loginResponse;

    /**
     * This is called to login a existing user.
     * Takes a LoginRequest as the parameter and returns a LoginResponse.
     *
     * @param request   A LoginRequest Object.
     * @return          A LoginResponse Object.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException {
        this.loginRequest = request;
        processServiceRequest();    // Sets up the ServerFacade and calls the doServiceSpecificTask.
        return loginResponse;
    }

    /**
     * Loads the profile image data for the user.
     *
     * @param user  The user whose profile image data is to be loaded as a byte array.
     * @throws IOException
     */
    public void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    /**
     * This is the primary method in the Template pattern of the BaseService Class.
     * This will login a user by calling the login method in the server facade
     * by passing it the provided loginRequest (which is first passed into login).
     * Returning the loginResponse is handled in the login() method.
     *
     * @throws IOException
     * @throws TweeterRemoteException
     */
    @Override
    public void doServiceSpecificTask() throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        this.loginResponse = serverFacade.login(loginRequest, URL_PATH);
        if(loginResponse.isSuccess()) {
            loadImage(loginResponse.getUser());
        }
    }
}
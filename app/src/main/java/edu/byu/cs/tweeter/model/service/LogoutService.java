package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.ILogoutService;

public class LogoutService extends BaseService implements ILogoutService {
    // The url_path extension for logout. (Can be found in AWS console -> API:Tweeter -> Stages -> dev tab)
    private static final String URL_PATH = "/logout";

    // Logout Request and Response Objects.
    LogoutRequest logoutRequest;
    LogoutResponse logoutResponse;

    /**
     * Performs a Logout of the current user.
     *
     * @param request   LogoutRequest Object.
     * @return          LogoutResponse Object
     * @throws IOException
     */
    public LogoutResponse logout(LogoutRequest request) throws IOException {
        this.logoutRequest = request;
        processServiceRequest();
        return logoutResponse;
    }

    /**
     * This is the primary method in the Template pattern of the BaseService Class.
     * This will logout a user by calling the logout method in the server facade
     * by passing it the provided logoutRequest (which is first passed into the logout method).
     * Returning the logoutResponse Object is handled in the logout() method.
     *
     * @throws IOException
     * @throws TweeterRemoteException
     */
    @Override
    public void doServiceSpecificTask() throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        this.logoutResponse = serverFacade.logout(logoutRequest, URL_PATH);
    }
}
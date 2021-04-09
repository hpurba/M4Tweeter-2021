package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutPresenter {

    private final View view;

    /**
     * This is the interface for the presenter's view (MainActivity).
     * It is the interface by which this presenter can communicate with it's view (call it's methods).
     * Methods listed here should be used only for retrieval, or raising an event (ex: change a button status).
     *  - If needed, specify methods here that will be called on the view in response to model updates.
     *
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        String getUserAlias();
    }

    /**
     * Creates an instance of a LogoutPresenter with the provided view (should be the MainActivity).
     * @param view View, which should be a MainActivity view for which this class is a presenter for.
     */
    public LogoutPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a logout request.
     * @return  LogoutResponse Object.
     * @throws IOException
     */
    public LogoutResponse logout() throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest(view.getUserAlias()); // Alias is the @username

        LogoutService logoutService = new LogoutService();
        return logoutService.logout(logoutRequest);
    }

    /**
     * Performs a retrieval of a new LogoutService
     * @return  A new LogoutService Object.
     */
    LogoutService getLogoutService() {
        return new LogoutService();
    }
}

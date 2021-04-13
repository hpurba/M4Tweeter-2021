package edu.byu.cs.tweeter.model.service.request;

public class LogoutRequest {

    private String username;
    private String authToken;

    /**
     * Empty Default constructor
     */
    public LogoutRequest() { }

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged out.
     */
    public LogoutRequest(String username) {
        this.username = username;
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

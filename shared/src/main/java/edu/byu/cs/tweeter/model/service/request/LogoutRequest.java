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
    public LogoutRequest(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
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


// TODO: THIS IS WHAT THE LOGOUT REQUEST SHOULD LOOK LIKE FOR TESTING (JSON)
/*

{
    "username": "The User's username",
    "authToken": "823bb95d-f5f5-4b5c-8027-067c7ce24e55"
}

 */
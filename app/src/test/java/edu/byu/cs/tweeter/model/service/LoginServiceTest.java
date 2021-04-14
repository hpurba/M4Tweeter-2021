package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.ILoginService;

class LoginServiceTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    private LoginService loginServiceSpy;

    /**
     * Create a LoginService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        String username = "@hpurba";

        User currentUser = new User("Test", "User", null);

        User resultUser1 = new User(null, null,
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        String password = "password";

        String AUTH_TOKEN_KEY = "AuthTokenKey";
        AuthToken authToken = new AuthToken();

        // Requests (Valid and Invalid)
        validRequest = new LoginRequest(username, password);
        invalidRequest = new LoginRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LoginResponse(user, new AuthToken());
        failureResponse = new LoginResponse("An exception occurred");

        loginServiceSpy = Mockito.spy(new LoginService());
        Mockito.when(loginServiceSpy.login(validRequest)).thenReturn(successResponse);
        Mockito.when(loginServiceSpy.login(invalidRequest)).thenReturn(failureResponse);
    }

    /**
     * Verify that for successful requests the {@link ILoginService #login(LoginRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetLoginStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceSpy.login(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link FollowingStatusService #getFollowingStatus(FollowingStatusRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLoginStatus_invalidRequest_returnsNoLoginStatus() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceSpy.login(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
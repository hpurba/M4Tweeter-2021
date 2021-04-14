package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

class LogoutServiceTest {

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;

    private LogoutService logoutServiceSpy;

    /**
     * Create a LogoutService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException {
        String username = "@hpurba";
        String password = "password";
        User currentUser = new User("Test", "User", null);
        User resultUser1 = new User(null, null,
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        String AUTH_TOKEN_KEY = "AuthTokenKey";
//        AuthToken authToken = new AuthToken();

        // Requests (Valid and Invalid)
//        validRequest = new LogoutRequest(username);
//        invalidRequest = new LogoutRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LogoutResponse(true); // success is true and message is null
        failureResponse = new LogoutResponse(false);

        logoutServiceSpy = Mockito.spy(new LogoutService());
        Mockito.when(logoutServiceSpy.logout(validRequest)).thenReturn(successResponse);
        Mockito.when(logoutServiceSpy.logout(invalidRequest)).thenReturn(failureResponse);
    }

    /**
     * Verify that for successful requests the {@link LogoutService #login(LoginRequest)}
     * method returns the same result as the {@link ServerFacadeOriginalM2}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetLogoutStatus_validRequest_correctResponse() throws IOException {
        LogoutResponse response = logoutServiceSpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }


    /**
     * Verify that for failed requests the {@link FollowingStatusService #getFollowingStatus(FollowingStatusRequest)}
     * method returns the same result as the {@link ServerFacadeOriginalM2}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogoutStatus_invalidRequest_returnsNoLoginStatus() throws IOException {
        LogoutResponse response = logoutServiceSpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}
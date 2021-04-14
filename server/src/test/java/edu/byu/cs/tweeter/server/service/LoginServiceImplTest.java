package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.ILoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceImplTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    private LoginServiceImpl loginServiceImplSpy;

    /**
     * Create a LoginService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    //TODO last 3 services
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
//        AuthToken authToken = new AuthToken();

        // Requests (Valid and Invalid)
        validRequest = new LoginRequest(username, password);
        invalidRequest = new LoginRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LoginResponse(user, AUTH_TOKEN_KEY);
        failureResponse = new LoginResponse("An exception occurred");

        loginServiceImplSpy = Mockito.mock(LoginServiceImpl.class);
        Mockito.when(loginServiceImplSpy.login(validRequest)).thenReturn(successResponse);
        Mockito.when(loginServiceImplSpy.login(invalidRequest)).thenReturn(failureResponse);
    }


    /**
     * Verify that for successful requests the {@link LoginServiceImpl #login(LoginRequest)}
     * method returns the same result as the {@link }.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetLoginStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceImplSpy.login(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link LoginServiceImpl #login(LoginRequest)}
     * method returns the same result as the {@link edu.byu.cs.tweeter.server.dao.LoginDAO}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLoginStatus_invalidRequest_returnsNoLoginStatus() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceImplSpy.login(invalidRequest);
        assertEquals(failureResponse, response);
    }

}

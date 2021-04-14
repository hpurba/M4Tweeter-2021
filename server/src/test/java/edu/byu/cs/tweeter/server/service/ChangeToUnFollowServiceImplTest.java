package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeToUnFollowServiceImplTest {
    private FollowingStatusRequest validRequest;
    private FollowingStatusRequest invalidRequest;

    private FollowingStatusResponse validResponse;
    private FollowingStatusResponse invalidResponse;

    private ChangeToUnFollowServiceImpl implSpy;

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

//        validRequest = new FollowingStatusRequest(user, true);
//        invalidRequest = new FollowingStatusRequest(null, null);

//        validResponse = new FollowingStatusResponse(username, false);
        invalidResponse = new FollowingStatusResponse("An exception occurred");

        implSpy = Mockito.mock(ChangeToUnFollowServiceImpl.class);
        Mockito.when(implSpy.getFollowingStatus(validRequest)).thenReturn(validResponse);
        Mockito.when(implSpy.getFollowingStatus(invalidRequest)).thenReturn(invalidResponse);

    }

    @Test
    public void testChangeToFollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingStatusResponse response = implSpy.getFollowingStatus(validRequest);
        assertEquals(validResponse, response);
    }

    @Test
    public void testChangeToFollow_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        FollowingStatusResponse response = implSpy.getFollowingStatus(invalidRequest);
        assertEquals(invalidResponse, response);
    }

}

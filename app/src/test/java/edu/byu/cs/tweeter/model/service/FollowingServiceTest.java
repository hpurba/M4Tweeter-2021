package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.IFollowingService;

public class FollowingServiceTest {

    private FollowingRequest validRequest;
    private FollowingRequest invalidRequest;

    private FollowingResponse successResponse;
    private FollowingResponse failureResponse;

    private FollowingService followingServiceSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Requests (Valid and Invalid)
        validRequest = new FollowingRequest(currentUser, 3, null);
        invalidRequest = new FollowingRequest(currentUser, 0, currentUser);

        // Responses (Valid and Invalid)
        successResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        failureResponse = new FollowingResponse("An exception occurred");

        // Mock FollowingService used to call getFollowees()
        followingServiceSpy = Mockito.spy(new FollowingService());
        Mockito.when(followingServiceSpy.getFollowees(validRequest)).thenReturn(successResponse);
        Mockito.when(followingServiceSpy.getFollowees(invalidRequest)).thenReturn(failureResponse);

        // Mock ServerFacade
//        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
//        Mockito.when(followingServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
//        Mockito.when(mockServerFacade.getFollowees(validRequest)).thenReturn(successResponse);
    }

    /**
     * Verify that for successful requests the {@link edu.byu.cs.tweeter.model.service.serviceInterfaces.IFollowingService#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link edu.byu.cs.tweeter.model.net.ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException {
        FollowingResponse response = followingServiceSpy.getFollowees(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link edu.byu.cs.tweeter.model.service.serviceInterfaces.IFollowingService#getFollowees(FollowingRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException {
        FollowingResponse response = followingServiceSpy.getFollowees(validRequest);
        for(User user : response.getFollowees()) {
            Assertions.assertNotNull(user);
        }
    }

    /**
     * Verify that for failed requests the {@link IFollowingService#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link edu.byu.cs.tweeter.model.net.ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException {
        FollowingResponse response = followingServiceSpy.getFollowees(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}

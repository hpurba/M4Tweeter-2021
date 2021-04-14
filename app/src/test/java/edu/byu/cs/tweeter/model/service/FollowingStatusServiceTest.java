//package edu.byu.cs.tweeter.model.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//
//import edu.byu.cs.tweeter.model.domain.User;
//import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
//import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;
//
//class FollowingStatusServiceTest {
//
//    private FollowingStatusRequest validRequest;
//    private FollowingStatusRequest invalidRequest;
//
//    private FollowingStatusResponse successResponse;
//    private FollowingStatusResponse failureResponse;
//
//    private FollowingStatusService followingStatusServiceSpy;
//
//    /**
//     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
//     * requests.
//     */
//    @BeforeEach
//    public void setup() throws IOException {
//        User currentUser = new User("FirstName", "LastName", null);
//
//        User resultUser1 = new User("FirstName1", "LastName1",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//        User resultUser2 = new User("FirstName2", "LastName2",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
//        User resultUser3 = new User("FirstName3", "LastName3",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
//
//
//        String myUsername = "HikaruPurba";
//        String otherPersonUsername = "NillyBilly";
//        Boolean isFollowing = true;
//
//
//
//
//        // Setup request objects to use in the tests
//        validRequest = new FollowingStatusRequest(currentUser, true);
//        invalidRequest = new FollowingStatusRequest(resultUser1,  false);
//
//        // Setup a mock ServerFacade that will return known responses
//        successResponse = new FollowingStatusResponse(currentUser.getAlias(), false);
//        failureResponse = new FollowingStatusResponse("An exception occurred");
//
//
//        followingStatusServiceSpy = Mockito.spy(new FollowingStatusService());
//        Mockito.when(followingStatusServiceSpy.getFollowingStatus(validRequest)).thenReturn(successResponse);
//        Mockito.when(followingStatusServiceSpy.getFollowingStatus(invalidRequest)).thenReturn(failureResponse);
//    }
//
//    /**
//     * Verify that for successful requests the {@link FollowingStatusService #getFollowingStatus(FollowingStatusRequest)}
//     * method returns the same result as the {@link edu.byu.cs.tweeter.model.net.ServerFacade}.
//     * .
//     *
//     * @throws IOException if an IO error occurs.
//     */
//    @Test
//    public void testGetFollowingStatus_validRequest_correctResponse() throws IOException {
//        FollowingStatusResponse response = followingStatusServiceSpy.getFollowingStatus(validRequest);
//        Assertions.assertEquals(successResponse, response);
//    }
//
//
//    /**
//     * Verify that for failed requests the {@link FollowingStatusService #getFollowingStatus(FollowingStatusRequest)}
//     * method returns the same result as the {@link edu.byu.cs.tweeter.model.net.ServerFacade}.
//     *
//     * @throws IOException if an IO error occurs.
//     */
//    @Test
//    public void testGetFollowingStatus_invalidRequest_returnsNoFollowingStatus() throws IOException {
//        FollowingStatusResponse response = followingStatusServiceSpy.getFollowingStatus(invalidRequest);
//        Assertions.assertEquals(failureResponse, response);
//    }
//
//}
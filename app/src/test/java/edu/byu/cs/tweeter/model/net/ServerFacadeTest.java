//package edu.byu.cs.tweeter.model.net;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import edu.byu.cs.tweeter.model.domain.User;
//import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
//import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
//
//class ServerFacadeTest {
//    private static final String URL_PATH = "/following";
//
//    private final User user1 = new User("Daffy", "Duck", "");
//    private final User user2 = new User("Fred", "Flintstone", "");
//    private final User user3 = new User("Barney", "Rubble", "");
//    private final User user4 = new User("Wilma", "Rubble", "");
//    private final User user5 = new User("Clint", "Eastwood", "");
//    private final User user6 = new User("Mother", "Teresa", "");
//    private final User user7 = new User("Harriett", "Hansen", "");
//    private final User user8 = new User("Zoe", "Zabriski", "");
//
//    private ServerFacade serverFacadeSpy;
//
//
//    @BeforeEach
//    void setup() {
//        serverFacadeSpy = Mockito.spy(new ServerFacade());
//    }
//
//    @Test
//    void testGetFollowees_noFolloweesForUser() throws IOException, TweeterRemoteException {
//        List<User> followees = Collections.emptyList();
////        Mockito.when(serverFacadeSpy.getFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user1, 10, null);
//        FollowingResponse response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        Mockito.when(serverFacadeSpy.getFollowees(request, "")).thenReturn(followees);
//
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertTrue(response.getHasMorePages());
//    }
//
//    @Test
//    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() throws IOException, TweeterRemoteException {
//        List<User> followees = Collections.singletonList(user2);
//        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user1, 10, null);
//        FollowingResponse response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertFalse(response.getFollowees().contains(user2));
//        Assertions.assertTrue(response.getHasMorePages());
//    }
//
//    @Test
//    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() throws IOException, TweeterRemoteException {
//        List<User> followees = Arrays.asList(user2, user3);
//        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user3, 8, null);
//        FollowingResponse response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertFalse(response.getFollowees().contains(user2));
//        Assertions.assertFalse(response.getFollowees().contains(user3));
//        Assertions.assertTrue(response.getHasMorePages());
//    }
//
//    @Test
//    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() throws IOException, TweeterRemoteException {
//        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7);
//        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user5, 8, null);
//        FollowingResponse response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        // Verify first page
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertFalse(response.getFollowees().contains(user2));
//        Assertions.assertFalse(response.getFollowees().contains(user3));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify second page
//        request = new FollowingRequest(user5, 8, response.getFollowees().get(1));
//        response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertFalse(response.getFollowees().contains(user4));
//        Assertions.assertFalse(response.getFollowees().contains(user5));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify third page
//        request = new FollowingRequest(user5, 8, response.getFollowees().get(1));
//        response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertFalse(response.getFollowees().contains(user6));
//        Assertions.assertFalse(response.getFollowees().contains(user7));
//        Assertions.assertTrue(response.getHasMorePages());
//    }
//
//
//    @Test
//    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() throws IOException, TweeterRemoteException {
//        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7, user8);
//        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user6, 8, null);
//        FollowingResponse response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        // Verify first page
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertFalse(response.getFollowees().contains(user2));
//        Assertions.assertFalse(response.getFollowees().contains(user3));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify second page
//        request = new FollowingRequest(user6, 8, response.getFollowees().get(1));
//        response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertFalse(response.getFollowees().contains(user4));
//        Assertions.assertFalse(response.getFollowees().contains(user5));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify third page
//        request = new FollowingRequest(user6, 8, response.getFollowees().get(1));
//        response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertFalse(response.getFollowees().contains(user6));
//        Assertions.assertFalse(response.getFollowees().contains(user7));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify fourth page
//        request = new FollowingRequest(user6, 8, response.getFollowees().get(1));
//        response = serverFacadeSpy.getFollowees(request, URL_PATH);
//
//        Assertions.assertEquals(8, response.getFollowees().size());
//        Assertions.assertFalse(response.getFollowees().contains(user8));
//        Assertions.assertTrue(response.getHasMorePages());
//    }
//}

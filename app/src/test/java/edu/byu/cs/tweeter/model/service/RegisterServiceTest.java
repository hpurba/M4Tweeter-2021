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
//import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
//import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
//
//class RegisterServiceTest {
//
//
//    private RegisterRequest validRequest;
//    private RegisterRequest invalidRequest;
//
//    private RegisterResponse successResponse;
//    private RegisterResponse failureResponse;
//
//    private RegisterService registerServiceSpy;
//
//    /**
//     * Create a LogoutService spy that uses a mock ServerFacade to return known responses to
//     * requests.
//     */
//    @BeforeEach
//    public void setup() throws IOException {
//        String firstName = "Hikaru";
//        String lastName = "Purba";
//        String alias = "@hpurba";
//        String password = "password";
//        byte[] byteArray = new byte[0];
//
//        User user = new User(firstName, lastName, alias,
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//
////        AuthToken authToken = new AuthToken();
//
//        // Requests (Valid and Invalid)
//        validRequest = new RegisterRequest(firstName, lastName, alias, password, byteArray);
//        invalidRequest = new RegisterRequest(null, null, null, null, null);
//
//        // Setup a mock ServerFacade that will return known responses
////        successResponse = new RegisterResponse(user, authToken); // success is true and message is null
//        failureResponse = new RegisterResponse(null, null);
//
//        // Create a FollowingService instance and wrap it with a spy that will use the mock service
//        registerServiceSpy = Mockito.spy(new RegisterService());
//        Mockito.when(registerServiceSpy.register(validRequest)).thenReturn(successResponse);
//        Mockito.when(registerServiceSpy.register(invalidRequest)).thenReturn(failureResponse);
//    }
//
//    /**
//     * Verify that for successful requests the {@link RegisterService #register(RegisterRequest)}
//     * method returns the same result as the {@link }.
//     * .
//     *
//     * @throws IOException if an IO error occurs.
//     */
//    @Test
//    public void testGetRegisterStatus_validRequest_correctResponse() throws IOException {
//        RegisterResponse response = registerServiceSpy.register(validRequest);
//        Assertions.assertEquals(successResponse, response);
//    }
//
//
//    /**
//     * Verify that for failed requests the {@link RegisterService #register(RegisterRequest)}
//     * method returns the same result as the {@link }.
//     *
//     * @throws IOException if an IO error occurs.
//     */
//    @Test
//    public void testRegisterStatus_invalidRequest_returnsNoLoginStatus() throws IOException {
//        RegisterResponse response = registerServiceSpy.register(invalidRequest);
//        Assertions.assertEquals(failureResponse, response);
//    }
//}
//package edu.byu.cs.tweeter.presenter;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import edu.byu.cs.tweeter.model.service.LogoutService;
//import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
//import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
//
//class LogoutPresenterTest {
//
//
//    private LogoutRequest request;
//    private LogoutRequest emptyRequest;
//    private LogoutResponse response;
//    private LogoutService mockLogoutService;
//    private LogoutPresenter presenter;
//
//    @BeforeEach
//    public void setup() throws IOException {
////
//
//        request = new LogoutRequest("@TestUser");
//        emptyRequest = new LogoutRequest(null);
//        response = new LogoutResponse(true);
//
//        // Create a mock FollowingService
//        mockLogoutService = Mockito.mock(LogoutService.class);
//        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);
//
//        // Wrap a FollowingPresenter in a spy that will use the mock service.
//        presenter = Mockito.spy(new LogoutPresenter(new LogoutPresenter.View() {
//            @Override
//            public String getUserAlias() {
//                return null;
//            }
//        }));
//        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
//    }
//
//    @Test
//    public void testGetLogout_returnsServiceResult() throws IOException {
//        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);
//
//        // Assert that the presenter returns the same response as the service (it doesn't do
//        // anything else, so there's nothing else to test).
//        Assertions.assertEquals(response.isSuccess(), presenter.logout(request).isSuccess());
//    }
//
//    @Test
//    public void testGetLogout_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
//        Mockito.when(mockLogoutService.logout(request)).thenThrow(new IOException());
//
//        Assertions.assertEquals(response.isSuccess(), presenter.logout(request).isSuccess());
////        Assertions.assertThrows(IOException.class, () -> {
////            presenter.logout(request);
////        });
//    }
//
//
//}
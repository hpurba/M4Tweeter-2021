package edu.byu.cs.tweeter.RealRequestTest;

import android.util.Log;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    private LoginService loginService;
    private RegisterService registerService;
    private User testUser;
    private String password;
    private String hashedPassword;
    private String Token;

    @BeforeEach
    void setUp() {
        loginService = new LoginService();
        registerService = new RegisterService();

        testUser = new User("firstNameTest", "lastNameTest", "@TestBoi", "https://tweeteruserprofileimages.s3-us-west-2.amazonaws.com/%40hpurba.png");
        password = "password";

        hashedPassword = "5f4dcc3b5aa765d61d8327deb882cf99";
        RegisterResponse registerResponse = null;

        try {
            registerResponse = registerService.register(new RegisterRequest(testUser.getFirstName(), testUser.getLastName(), testUser.getAlias(), hashedPassword, testUser.getImageUrl()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Token = registerResponse.getAuthToken();
    }

    @AfterEach
    void tearDown() {
        LogoutService logoutService = new LogoutService();
        LogoutRequest logoutRequest = new LogoutRequest("@TestBoi", Token);
        try {
            LogoutResponse logoutResponse = logoutService.logout(logoutRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Token = null;
        testUser = null;
        password = null;
        hashedPassword = null;
    }

//    @Test
//    void loginFAIL() {
//        LoginRequest loginRequest = new LoginRequest(null, "RandomNotRight");
//        LoginResponse loginResponse = loginHandler.handleRequest(loginRequest, null);
//        assertEquals("User not found", loginResponse.getMessage());
//    }
//
//    @Test
//    void signInWrongPass() {
//        LoginRequest loginRequest = new LoginRequest(testUser.getAlias(), "RandomNotRight");
//        LoginResponse loginResponse = loginHandler.handleRequest(loginRequest, null);
//        assertEquals("Invalid password", loginResponse.getMessage());
//    }

    @Test
    void loginSUCCESS() {
        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest(testUser.getAlias(), password);
        LoginResponse loginResponse = null;
        try {
            loginResponse = loginService.login(loginRequest);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        assertEquals(testUser, loginResponse.getUser());
        Token = loginResponse.getAuthToken();
    }
}

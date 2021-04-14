package edu.byu.cs.tweeter.server.DAOTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.AuthorizationsDAO;
import edu.byu.cs.tweeter.server.dao.UsersDAO;
import edu.byu.cs.tweeter.server.lambda.LoginHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginDAOTest {
    private LoginHandler loginHandler;
    private User testUser;
    private String password;
    private String hashedPassword;
    private String Token;

    @BeforeEach
    void setUp() {
        loginHandler = new LoginHandler();

        testUser = new User("firstNameTest", "lastNameTest", "@TestBoi", "https://tweeteruserprofileimages.s3-us-west-2.amazonaws.com/%40hpurba.png");
        password = "password";

        UsersDAO usersDAO = new UsersDAO();

        hashedPassword = "5f4dcc3b5aa765d61d8327deb882cf99";
        RegisterResponse registerResponse = usersDAO.register(new RegisterRequest(testUser.getFirstName(), testUser.getLastName(), testUser.getAlias(), hashedPassword, testUser.getImageUrl()));
        Token = registerResponse.getAuthToken();
    }

    @AfterEach
    void tearDown() {
        UsersDAO usersDAO = new UsersDAO();
        usersDAO.deleteUser(testUser);
        AuthorizationsDAO authsDAO = new AuthorizationsDAO();

        authsDAO.deleteToken(Token);
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
        LoginRequest loginRequest = new LoginRequest(testUser.getAlias(), password);
        LoginResponse loginResponse = loginHandler.handleRequest(loginRequest, null);
        assertEquals(testUser, loginResponse.getUser());
        Token = loginResponse.getAuthToken();
    }
}

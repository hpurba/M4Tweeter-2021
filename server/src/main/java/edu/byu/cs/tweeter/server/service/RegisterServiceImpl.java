package edu.byu.cs.tweeter.server.service;

import java.sql.Timestamp;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.IRegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.AuthorizationsDAO;
import edu.byu.cs.tweeter.server.dao.RegisterDAO;

public class RegisterServiceImpl extends ServiceImpl implements IRegisterService {


    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private User registeringUser;

    public RegisterResponse register(RegisterRequest request) {

        // AuthorizationDAO and UsersDAO
        AuthorizationsDAO authorizationsDAO = new AuthorizationsDAO();
//        UsersDAO usersDAO = new UsersDAO(); // Do a usersDAO


//        request.setPassword(hashPassword(request.getPassword()));

        registeringUser = new User(request.getFirstName(), request.getLastName(), MALE_IMAGE_URL);

        String authToken = UUID.randomUUID().toString();

        RegisterResponse registerResponse = new RegisterResponse(registeringUser, authToken);
        registerResponse.setSuccess(true);

//        String token = authToken.toString();
        long curr_time = new Timestamp(System.currentTimeMillis()).getTime();
        authorizationsDAO.addToken(authToken, String.valueOf(curr_time));

        return registerResponse;
    }

    RegisterDAO registerDAO() { return new RegisterDAO(); }
}
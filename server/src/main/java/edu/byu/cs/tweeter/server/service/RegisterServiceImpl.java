package edu.byu.cs.tweeter.server.service;

import java.sql.Timestamp;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.IRegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.AuthorizationsDAO;
import edu.byu.cs.tweeter.server.dao.RegisterDAO;

public class RegisterServiceImpl extends ServiceImpl implements IRegisterService {


    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user1 = new User("firstname", "lastname", MALE_IMAGE_URL);


    public RegisterResponse register(RegisterRequest request) {

        AuthorizationsDAO authorizationsDAO = new AuthorizationsDAO();

//        request.setPassword(hashPassword(request.getPassword()));

        AuthToken authToken = new AuthToken();
        RegisterResponse registerResponse = new RegisterResponse(user1, authToken);
        registerResponse.setSuccess(true);

        String token = authToken.toString();
        long curr_time = new Timestamp(System.currentTimeMillis()).getTime();
        authorizationsDAO.addToken(token, String.valueOf(curr_time));

        return registerResponse;
    }

    RegisterDAO registerDAO() { return new RegisterDAO(); }
}
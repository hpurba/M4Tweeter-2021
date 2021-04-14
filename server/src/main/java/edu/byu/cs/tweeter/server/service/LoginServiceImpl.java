package edu.byu.cs.tweeter.server.service;

import java.sql.Timestamp;
import java.util.UUID;

import edu.byu.cs.tweeter.model.service.serviceInterfaces.ILoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.AuthorizationsDAO;
import edu.byu.cs.tweeter.server.dao.UsersDAO;

/**
 * Login services layer to which the handler delegates each request.
 */
public class LoginServiceImpl extends ServiceImpl implements ILoginService {

    @Override
    public LoginResponse login(LoginRequest request) {
        // AuthorizationDAO and UsersDAO
        AuthorizationsDAO authorizationsDAO = new AuthorizationsDAO();
        UsersDAO usersDAO = new UsersDAO();

        try {
            request.setPassword(hashPassword(request.getPassword()));
        } catch (Exception exception) {
            throw new RuntimeException("[BadRequest500] 500 : Could not set hashed password in request." + exception.toString());
        }

        // Login using UsersDAO (verifies user and password)
        LoginResponse response = usersDAO.login(request);

        if (response.isSuccess()) {
            String authToken = UUID.randomUUID().toString();
            long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
            authorizationsDAO.addToken(authToken, String.valueOf(currentTime));
            response.setAuthToken(authToken);
        }

        return response;
    }
}

// 400 vs 500 lvl exceptions:
//https://enterprisecraftsmanship.com/posts/rest-api-response-codes-400-vs-500/
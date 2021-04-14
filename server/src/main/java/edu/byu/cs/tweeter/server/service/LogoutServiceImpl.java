package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.ILogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.dao.AuthorizationsDAO;
import edu.byu.cs.tweeter.server.dao.LogoutDAO;

public class LogoutServiceImpl implements ILogoutService {

    public LogoutResponse logout(LogoutRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getUsername() != null && request.getClass() == null) {
            throw new RuntimeException("[BadRequest500] 500");
        }

        authorizationsDAO().deleteToken(request.getAuthToken());
        LogoutResponse logoutResponse = new LogoutResponse();
        return logoutResponse;
    }
    AuthorizationsDAO authorizationsDAO() { return new AuthorizationsDAO(); }
}

package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.IRegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.RegisterDAO;

public class RegisterServiceImpl implements IRegisterService {

    public RegisterResponse register(RegisterRequest request) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400");
        } else if (request.getAlias() != null && request.getPassword() == null) {
            throw new RuntimeException("[BadRequest500] 500");
        }
        return registerDAO().register(request);
    }

    RegisterDAO registerDAO() { return new RegisterDAO(); }
}
package edu.byu.cs.tweeter.server.service;

import java.sql.Timestamp;
import java.util.UUID;

import edu.byu.cs.tweeter.model.service.serviceInterfaces.IRegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.AuthorizationsDAO;
import edu.byu.cs.tweeter.server.dao.UsersDAO;

public class RegisterServiceImpl extends ServiceImpl implements IRegisterService {

    public RegisterResponse register(RegisterRequest request) {
        // AuthorizationDAO and UsersDAO
        AuthorizationsDAO authorizationsDAO = new AuthorizationsDAO();
        UsersDAO usersDAO = new UsersDAO();

        // Build RegisterRequest
        try {
            // Upload Image to S3 and set imageURL to its location.
            request.setProfileImageURL(uploadImageToS3(request.getAlias(), request.getByteArray()));
            // Replace password with Hashed Password.
            request.setPassword(hashPassword(request.getPassword()));
        } catch ( Exception exception) {
            throw new RuntimeException("[BadRequest500] 500 : Could not process image or generate hashed password." + exception.toString());
        }

        // Register
        RegisterResponse registerResponse = usersDAO.register(request);

        if (registerResponse.isSuccess()) {
            // Generate AuthToken and add it to authorizations table using the authorizationsDAO
            String authToken = UUID.randomUUID().toString();
            long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
            authorizationsDAO.addToken(authToken, String.valueOf(currentTime));
            registerResponse.setAuthToken(authToken);
        }

        return registerResponse;
    }
}
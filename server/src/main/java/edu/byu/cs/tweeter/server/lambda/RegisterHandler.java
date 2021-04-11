package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.service.RegisterServiceImpl;

/**
 * An AWS lambda function that registers a user and returns the user object and an auth code for
 * a successful login.
 */
public class RegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {
    @Override
    public RegisterResponse handleRequest(RegisterRequest registerRequest, Context context) {

//        if (registerRequest.getFirstName().isEmpty() || registerRequest.getFirstName() == null
//                || registerRequest.getLastName().isEmpty() || registerRequest.getLastName() == null
//                || registerRequest.getAlias().isEmpty() || registerRequest.getAlias() == null
//                || registerRequest.getPassword().isEmpty() || registerRequest.getPassword() == null
//                || registerRequest.getByteArray().length == 0 || registerRequest.getByteArray() == null) {
//            throw new RuntimeException("[BadRequest400] 400");
//        }

        RegisterServiceImpl registerService = new RegisterServiceImpl();
        try {
            return registerService.register(registerRequest);
        } catch (Exception e) {
//            System.out.println(e.toString());
            throw new RuntimeException("[BadRequest500] 500");
        }
    }
}
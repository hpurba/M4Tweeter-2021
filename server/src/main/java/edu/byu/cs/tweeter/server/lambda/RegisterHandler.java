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

        // FirstName
        if(registerRequest.getFirstName() == null) {
            throw new RuntimeException("[BadRequest400] 400. FirstName is null:" + registerRequest.getFirstName());
        }
        // LastName
        if(registerRequest.getLastName() == null) {
            throw new RuntimeException("[BadRequest400] 400. LastName is null:" + registerRequest.getLastName());
        }
        // Alias
        if(registerRequest.getAlias() == null) {
            throw new RuntimeException("[BadRequest400] 400. Alias is null:" + registerRequest.getAlias());
        }
        // Password
        if(registerRequest.getPassword() == null) {
            throw new RuntimeException("[BadRequest400] 400. Password is null:" + registerRequest.getPassword());
        }
        // Byte Array is length 0
        if(registerRequest.getByteArray() == null) {
            throw new RuntimeException("[BadRequest400] 400. ByteArray is null:" + registerRequest.getByteArray());
        }


        RegisterServiceImpl registerService = new RegisterServiceImpl();
        try {
            return registerService.register(registerRequest);
        } catch (Exception e) {
//            System.out.println(e.toString());
            throw new RuntimeException("[BadRequest500] 500 : " + e.toString());
        }
    }
}

// This is used to test the API Gateway
/*
{
    "firstName": "hikaru",
    "lastName": "purba",
    "alias": "hpurba",
    "byteArray": null,
    "password": "asdf"
}
 */

// Condensed version of what I have up top.
//        if (registerRequest.getFirstName().isEmpty() || registerRequest.getFirstName() == null
//                || registerRequest.getLastName().isEmpty() || registerRequest.getLastName() == null
//                || registerRequest.getAlias().isEmpty() || registerRequest.getAlias() == null
//                || registerRequest.getPassword().isEmpty() || registerRequest.getPassword() == null
//                || registerRequest.getByteArray().length == 0 || registerRequest.getByteArray() == null) {
//            throw new RuntimeException("[BadRequest400] 400");
//        }
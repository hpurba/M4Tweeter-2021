package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterDAO {

    // Dummy Data
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user1 = new User("firstname", "lastname", MALE_IMAGE_URL);

    public RegisterResponse register(RegisterRequest request) {

        byte[] byteArray = request.getByteArray();

        User user = new User(request.getFirstName(),
                request.getLastName(),
                request.getAlias(),
                MALE_IMAGE_URL);
        user.setImageBytes(byteArray);

        AuthToken authToken = new AuthToken();
        RegisterResponse registerResponse = new RegisterResponse(user1, authToken);
        return registerResponse;
    }
}
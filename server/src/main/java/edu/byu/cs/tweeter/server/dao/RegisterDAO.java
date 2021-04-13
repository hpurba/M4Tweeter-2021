// TODO: DELETE THIS ONCE REGISTERING A NEW USER IS COMPLETELY WORKING.

//package edu.byu.cs.tweeter.server.dao;
//
//import java.util.UUID;
//
//import edu.byu.cs.tweeter.model.domain.AuthToken;
//import edu.byu.cs.tweeter.model.domain.User;
//import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
//import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
//
//public class RegisterDAO {
//
//    // Dummy Data
//    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
//    private final User user1 = new User("firstname", "lastname", MALE_IMAGE_URL);
//    // {"success":true,"user":{"firstName":"firstname","lastName":"lastname","alias":"@firstnamelastname","imageUrl":"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png","followersCount":0,"followingCount":0,"name":"firstname lastname"},"authToken":{}}
//
//
//    public RegisterResponse register(RegisterRequest request) {
//
////        String byteArray = request.getByteArray();
//
//        User user = new User(request.getFirstName(),
//                request.getLastName(),
//                request.getAlias(),
//                MALE_IMAGE_URL);
////        user.setImageBytes(byteArray);
//
//
//        String authToken = UUID.randomUUID().toString();
//        RegisterResponse registerResponse = new RegisterResponse(user1, authToken);
//        return registerResponse;
//    }
//}
package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class UsersDAO {
    // Table Name and attributes (columns)
    private static final String TableName = "users";
    private static final String UsernameAttribute = "alias";    // This is the partition/sort key.
    private static final String FirstNameAttribute = "first_name";
    private static final String LastNameAttribute = "last_name";
    private static final String ProfileImageURLAttribute = "image_url";
    private static final String PasswordAttribute = "password";

    // AWS DynamoDB Client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    /**
     * Registering User.
     * Creates a new User Item to add to add to the DynamoDB table.
     * @param request
     * @return
     */
    public RegisterResponse register(RegisterRequest request) {
        Table table = dynamoDB.getTable(TableName);

        Item foundItem = table.getItem(UsernameAttribute, request.getAlias());
        if (foundItem != null) {
            return new RegisterResponse("User already exists, try logging in.");
        }

        Item newUser = new Item()
                .withPrimaryKey(UsernameAttribute, request.getAlias())
                .withString(FirstNameAttribute, request.getFirstName())
                .withString(LastNameAttribute, request.getLastName())
                .withString(ProfileImageURLAttribute, request.getProfileImageURL())
                .withString(PasswordAttribute, request.getPassword());
        table.putItem(newUser);

        User user = new User(request.getFirstName(), request.getLastName(), request.getAlias(), request.getProfileImageURL());

        // authtoken will be changed in the RegisterServiceImpl and added to the authorizations table using the AuthorizationsDAO
        return new RegisterResponse(user, "token");
    }

    /**
     *
     * @param request
     * @return
     */
    public RegisterResponse login(RegisterRequest request) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(UsernameAttribute, request.getAlias());
        if (item == null) {
            return new RegisterResponse("User is not found");
        }

        String alias = item.getString(UsernameAttribute);
        String first_name = item.getString(FirstNameAttribute);
        String last_name = item.getString(LastNameAttribute);
        String image_url = item.getString(ProfileImageURLAttribute);
        String password = item.getString(PasswordAttribute);

        if (!password.equals(request.getPassword())) {
            return new RegisterResponse("Password is invalid");
        }

        return new RegisterResponse(new User(first_name, last_name, alias, image_url), "token");
    }


    public RegisterResponse getUser(RegisterRequest request) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(UsernameAttribute, request.getAlias());
        if (item == null) {
            return new RegisterResponse("Unable to find user");
        }

        String alias = item.getString(UsernameAttribute);
        String first_name = item.getString(FirstNameAttribute);
        String last_name = item.getString(LastNameAttribute);
        String profileImageURL = item.getString(ProfileImageURLAttribute);

        return new RegisterResponse(new User(first_name, last_name, alias, profileImageURL), "tempAuthTokenInUsersDAO");    // TODO: Fix this.
    }

    public void deleteUser(User user) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(UsernameAttribute, user.getAlias());
    }
}

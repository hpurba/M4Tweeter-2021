// TODO: KEEP

package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class AuthorizationsDAO {
    private static final String TableName = "authorizations";

    private static final String TokenAttr = "token";
    private static final String TimestampAttr = "creationtime";


    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    /**
     * Adds a token with a timestamp to the authorizations Table.
     * @param token
     * @param timestamp
     */
    public void addToken(String token, String timestamp) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item()
                .withPrimaryKey(TokenAttr, token)
                .withString(TimestampAttr, timestamp);
        table.putItem(item);
    }

    public void deleteToken(String token) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(TokenAttr, token);
    }


    /**
     * Tries to find a token in the authorizations Table.
     * @param token
     * @return
     */
    public long validateToken(String token) {
        Table table = dynamoDB.getTable(TableName);

        Item authorizationItem = table.getItem(TokenAttr, token);
        // Item not found
        if (authorizationItem == null) {
            return -1;
        }

        return Long.parseLong(authorizationItem.getString(TimestampAttr));
    }
}

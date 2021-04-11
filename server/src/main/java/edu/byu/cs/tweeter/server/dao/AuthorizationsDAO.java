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


    public String validateToken(String token) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(TokenAttr, token);
        if (item == null) {
            return null;
        }

        return item.getString(TimestampAttr);
    }
}

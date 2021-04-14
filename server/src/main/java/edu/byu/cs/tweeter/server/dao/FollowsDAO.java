package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingStatusResponse;

public class FollowsDAO {
    private static final String TableName = "Follows";
    private static final String IndexName = "followsIndex";

    // Attribute
    // Follower
    private static final String FollowerAliasAttribute = "followerAlias";
    private static final String FollowerProfileImageURLAttribute = "followerProfileImageURL";
    private static final String FollowerFirstNameAttribute = "followerFirstName";
    private static final String FollowerLastNameAttribute = "followerLastName";
    // Followee
    private static final String FolloweeAliasAttribute = "followeeAlias";
    private static final String FolloweeProfileImageURLAttr = "followeeProfileImageURL";
    private static final String FolloweeFNameAttr = "followeeFirstName";
    private static final String FolloweeLNameAttr = "followeeLastName";


    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    /**
     * Follow another user.
     * @param request
     * @return
     */
    public FollowingStatusResponse changeToFollow(FollowingStatusRequest request, User otherUser) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item()
                .withPrimaryKey(FollowerAliasAttribute, request.getUser().getAlias(), FolloweeAliasAttribute, otherUser.getAlias())
                .withString(FollowerFirstNameAttribute, request.getUser().getFirstName())
                .withString(FollowerLastNameAttribute, request.getUser().getLastName())
                .withString(FollowerProfileImageURLAttribute, request.getUser().getImageUrl())
                .withString(FolloweeFNameAttr, otherUser.getFirstName())
                .withString(FolloweeLNameAttr, otherUser.getLastName())
                .withString(FolloweeProfileImageURLAttr, otherUser.getImageUrl());
        table.putItem(item);

        return new FollowingStatusResponse(otherUser, true);
    }

    /**
     * Unfollow another user.
     * @param request
     * @return
     */
    public FollowingStatusResponse changeToUnFollow(FollowingStatusRequest request, User otherUser) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(FollowerAliasAttribute, request.getUser().getAlias(), FolloweeAliasAttribute, otherUser.getAlias());
        return new FollowingStatusResponse(otherUser, true);
    }

    /**
     * Retrieves all the Followers.
     * @param request   FollowerRequest Object.
     * @return          FollowerResponse Object.
     */
    public FollowerResponse getFollowers(FollowerRequest request) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#followee", FolloweeAliasAttribute);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":followeeAlias", new AttributeValue().withS(request.getFollowee().getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withIndexName(IndexName)
                .withKeyConditionExpression("#followee = :followeeAlias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.getLimit());

        if (request.getLastFollower() != null) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(FolloweeAliasAttribute, new AttributeValue().withS(request.getFollowee().getAlias()));
            startKey.put(FollowerAliasAttribute, new AttributeValue().withS(request.getLastFollower().getAlias()));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        List<User> followers = new ArrayList<>();
        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item: items) {
                String alias = item.get(FollowerAliasAttribute).getS();
                String first_name = item.get(FollowerFirstNameAttribute).getS();
                String last_name = item.get(FollowerLastNameAttribute).getS();
                String image_url = item.get(FollowerProfileImageURLAttribute).getS();
                followers.add(new User(first_name, last_name, alias, image_url));
            }
        }

        boolean hasMore = false;
        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            hasMore = true;
        }

        return new FollowerResponse(followers, hasMore);
    }

    /**
     * Retrieves all the Followees. (GetFollowing Tab)
     * @param request
     * @return
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#follower", FollowerAliasAttribute);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":followerAlias", new AttributeValue().withS(request.getFollower().getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#follower = :followerAlias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.getLimit());

        if (request.getLastFollowee() != null) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(FollowerAliasAttribute, new AttributeValue().withS(request.getFollower().getAlias()));
            startKey.put(FolloweeAliasAttribute, new AttributeValue().withS(request.getLastFollowee().getAlias()));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        List<User> followees = new ArrayList<>();
        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item: items) {
                String alias = item.get(FolloweeAliasAttribute).getS();
                String first_name = item.get(FolloweeFNameAttr).getS();
                String last_name = item.get(FolloweeLNameAttr).getS();
                String image_url = item.get(FolloweeProfileImageURLAttr).getS();
                followees.add(new User(first_name, last_name, alias, image_url));
            }
        }

        boolean hasMore = false;
        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            hasMore = true;
        }

        return new FollowingResponse(followees, hasMore);
    }

    /**
     * Checks if user is following other user.
     * @param request
     * @return
     */
    public FollowingStatusResponse checkFollow(FollowingStatusRequest request, User otherUser) {
        Table table = dynamoDB.getTable(TableName);

        boolean doesFollow = false;
        Item item = table.getItem(FollowerAliasAttribute, otherUser.getAlias(), FolloweeAliasAttribute, request.getUser().getAlias());
        if (item != null) {
            doesFollow = true;
        }
        return new FollowingStatusResponse(otherUser, doesFollow);
    }
}

// Follower: User
// Followee: OtherUser
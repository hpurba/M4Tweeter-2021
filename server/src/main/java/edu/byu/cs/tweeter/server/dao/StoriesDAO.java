// TODO: KEEP

package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;

public class StoriesDAO {
    private static final String TableName = "Stories";

    private static final String HandleAttribute = "Alias";
    private static final String TweetContentAttribute = "Content";
    private static final String TimestampAttribute = "Timestamp";
    private static final String FirstNameAttribute = "FirstName";
    private static final String LastNameAttribute = "LastName";
    private static final String ProfileImageURLAttribute = "ProfileImageURL";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);


    /**
     * Posts a Tweet
     * @param request
     * @return
     */
    public TweetResponse postTweet(TweetRequest request) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item()
                .withPrimaryKey(HandleAttribute, request.getUsername())
                .withNumber(TimestampAttribute, request.getTweet().getTimestamp())
                .withString(TweetContentAttribute, request.getTweetText())
                .withString(FirstNameAttribute, request.getTweet().getUser().getFirstName())
                .withString(LastNameAttribute, request.getTweet().getUser().getLastName())
                .withString(ProfileImageURLAttribute, request.getTweet().getUser().getImageUrl());
        table.putItem(item);

        return new TweetResponse(true, request.getUsername());
    }

    /**
     * Retrieves the tweets for the story of a user.
     * @param request
     * @return
     */
    public StoryTweetsResponse getStoryTweets(StoryTweetsRequest request) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#handle", HandleAttribute);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(request.getTweet().getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#handle = :alias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.getLimit());

        if (request.getLastTweet() != null) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(HandleAttribute, new AttributeValue().withS(request.getTweet().getAlias()));
            startKey.put(TimestampAttribute, new AttributeValue().withN(String.valueOf(request.getLastTweet().getTimestamp())));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        List<Tweet> tweets = new ArrayList<>();
        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item: items) {
                String alias = item.get(HandleAttribute).getS();
                String content = item.get(TweetContentAttribute).getS();
                long timestamp = Long.parseLong(item.get(TimestampAttribute).getN());
                String first_name = item.get(FirstNameAttribute).getS();
                String last_name = item.get(LastNameAttribute).getS();
                String image_url = item.get(ProfileImageURLAttribute).getS();

                User user = new User(first_name, last_name, alias, image_url);
                tweets.add(new Tweet(user, content, timestamp));
            }
        }

        boolean hasMore = false;
        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            hasMore = true;
        }

        if(tweets == null && lastKey == null) {
            hasMore = false;
            return new StoryTweetsResponse("No Tweets");
        }

        return new StoryTweetsResponse(tweets, hasMore);
    }




    // TODO: This may not be needed to be done, but.... This needs some error handling too
//    public void deleteTweet(User author, long timestamp) {
//        Table table = dynamoDB.getTable(TableName);
//        table.deleteItem(HandleAttr, author.getAlias(), TimestampAttr, timestamp);
//    }
}

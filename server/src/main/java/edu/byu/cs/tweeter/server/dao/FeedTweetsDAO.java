// TODO: DISCARD

package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.FeedTweetsResponse;

public class FeedTweetsDAO {

    private static final String TableName = "Feeds";

    private static final String HandleAttribute = "Alias";          // identifies who the feed belongs to // PARTITION KEY
    private static final String TimestampAttribute = "Timestamp";
    private static final String ContentAttribute = "Content";
    private static final String FirstNameAttribute = "FirstName";
    private static final String LastNameAttribute = "LastName";
    private static final String AuthorAttribute = "Author";
    private static final String ProfileImageURLAttribute = "ProfileImageURL";


    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public FeedTweetsResponse getFeedTweets(FeedTweetsRequest request) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#AliasHandle", HandleAttribute);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(request.getUser().getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#AliasHandle = :alias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.getLimit());

        if (request.getLastTweet() != null) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(HandleAttribute, new AttributeValue().withS(request.getUser().getAlias()));
            startKey.put(TimestampAttribute, new AttributeValue().withN(String.valueOf(request.getLastTweet().getTimestamp())));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        List<Tweet> tweets = new ArrayList<>();
        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item: items) {
                String alias = item.get(AuthorAttribute).getS();
                long timestamp = Long.parseLong(item.get(TimestampAttribute).getN());
                String content = item.get(ContentAttribute).getS();
                String first_name = item.get(FirstNameAttribute).getS();
                String last_name = item.get(LastNameAttribute).getS();
                String image_url = item.get(ProfileImageURLAttribute).getS();

                User author = new User(first_name, last_name, alias, image_url);
                tweets.add(new Tweet(author, content, timestamp));
            }
        }

        boolean hasMore = false;
        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            hasMore = true;
        }

        // No items in list
        if(tweets == null && lastKey == null) {
            hasMore = false;
            return new FeedTweetsResponse("No Tweets");
        }

        return new FeedTweetsResponse(tweets, hasMore);
    }

//    public ShareTweetResponse shareTweet(ShareTweetRequest request, User owner) {
//        Table table = dynamoDB.getTable(TableName);
//
//        User author = request.getTweet().getAuthor();
//
//        Item item = new Item()
//                .withPrimaryKey(HandleAttribute, owner.getAlias())
//                .withString(FirstNameAttribute, author.getFirstName())
//                .withString(LastNameAttribute, author.getLastName())
//                .withString(ProfileImageURLAttribute, author.getImageUrl())
//                .withString(ContentAttribute, request.getTweet().getContent())
//                .withNumber(TimestampAttribute, request.getTweet().getDate())
//                .withString(AuthorAttribute, author.getAlias());
//        table.putItem(item);
//
//        return new ShareTweetResponse();
//    }


    // TODO: Implement some exception catching and make sure it is actually needed.
//    public void deleteTweet(User owner, long timestamp) {
//        Table table = dynamoDB.getTable(TableName);
//        table.deleteItem(HandleAttribute, owner.getAlias(), TimestampAttribute, timestamp);
//    }





//    public FeedTweetsResponse getFeedTweets(FeedTweetsRequest request) {
//        // This is a List of all the dummy Tweets.
//        List<Tweet> allTweets = getDummyTweets();
//
//        // Set the request Limit to request.getLimit();
//        int requestLimit = request.getLimit();
//        if (requestLimit < 8) { // Ensure the request limit is at least 8.
//            requestLimit = 8;
//        }
//
//        // This is a List of the responseTweets
//        List<Tweet> responseTweets = new ArrayList<>(requestLimit);
//
//        boolean hasMorePages = true;
//        if( requestLimit > 0) {
//            int tweetsIndex = getTweetsStartingIndex(request.getLastTweet(), allTweets);    // 0 if the first element.
//
//            for(int limitCounter = 0; tweetsIndex < allTweets.size() && limitCounter < requestLimit; tweetsIndex++, limitCounter++) {
//                responseTweets.add(allTweets.get(tweetsIndex));
//            }
//
//            hasMorePages = tweetsIndex < allTweets.size();
//        }
//
//        return new FeedTweetsResponse(responseTweets, hasMorePages);  // Original
////        return new FeedTweetsResponse(allTweets, hasMorePages);
//    }

    /**
     * Determines the index for the first tweet in the specified 'allTweets' list that should
     * be returned in the current request. This will be the index of the next tweet after the
     * specified 'lastTweet'.
     *
     * @param lastTweet the last tweet that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allTweets the generated list of tweets from which we are returning paged results.
     * @return the index of the first tweet to be returned.
     */
//    private int getTweetsStartingIndex(Tweet lastTweet, List<Tweet> allTweets) {
//
//        int tweetsIndex = 0;
//
//        if(lastTweet != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allTweets.size(); i++) {
//                if(lastTweet.equals(allTweets.get(i))) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    tweetsIndex = i + 1;
//                    break;
//                }
//            }
//        }
//        return tweetsIndex;
//    }

}







/*

{
    "tweet": {
        "alias": "hpurba",
        "tweetText": "tweetText",
        "userName": "hpurba",
        {
        "type": "string",
        "description": "The User's username"
        }

    },
    "limit": {
        "description": "The User's username",
        "type": "number"
    },
    "lastTweet": {
      "type": "object",
      "properties": {
        "alias": {
        "type": "string",
        "description": "The User's alias"
        },
        "tweetText": {
        "type": "string",
        "description": "The User's tweet as text"
        },
        "userName": {
        "type": "string",
        "description": "The User's username"
        }
      }
    },
    "authToken": {
      "type": "string",
      "description": "The authToken attatched to this request."
}

 */

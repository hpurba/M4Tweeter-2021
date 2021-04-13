package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;
import edu.byu.cs.tweeter.server.service.TweetServiceImpl;

public class TweetHandler implements RequestHandler<TweetRequest, TweetResponse> {

    @Override
    public TweetResponse handleRequest(TweetRequest tweetRequest, Context context) {
        if (tweetRequest == null) {
            throw new RuntimeException("[BadRequest400] 400 : tweet request is null");
        } else if (tweetRequest.getTweet() == null || tweetRequest.getAuthToken() == null) {
            throw new RuntimeException("[BadRequest500] 500 : Missing tweet or authToken");
        }

        TweetServiceImpl tweetService = new TweetServiceImpl();
        return tweetService.tweet(tweetRequest);
    }
}


// TODO: THIS CAN BE USED TO TEST THE API GATEWAY FOR POSTING A TWEET
/*
{
    "tweet": {
        "user" : {
            "firstName": "hikaru",
            "lastName": "purba",
            "alias": "@hpurba",
            "imageUrl" : "https://tweeteruserprofileimages.s3-us-west-2.amazonaws.com/@hpurba.png",
            "byteArray": null,
            "followersCount": "0",
            "followingCount": "0"
        },
        "tweetText": "The User's tweet as text",
        "timestamp": "123"
    },
    "authToken": "91ea3688-ee0e-447d-b229-31eee9f5dde7"
}
 */
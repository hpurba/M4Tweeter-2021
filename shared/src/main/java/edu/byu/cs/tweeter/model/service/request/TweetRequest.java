package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Tweet;

public class TweetRequest {

    private Tweet tweet;
    private String authToken;

    /**
     * Empty Default constructor
     */
    public TweetRequest() { }

    public TweetRequest(Tweet tweet, String tweetText) {
        this.tweet = tweet;
        this.authToken = authToken;
    }

    public String getUsername() {
        return tweet.getAlias();
    }

    public String getTweetText() {
        return tweet.getTweetText();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
}




/*

// TODO: THIS IS THE MODEL SCHEMA
{
  "title": "TweetRequest",
  "type": "object",
  "properties": {
    "tweet": {
      "type": "object",
      "properties": {
        "user": {
            "type": "object",
            "properties": {
                "firstname": {
                    "type": "string",
                    "description": "The User's firstname"
                },
                "lastname": {
                    "type": "string",
                    "description": "The User's lastname"
                },
                "alias": {
                    "type": "string",
                    "description": "The User's alias"
                },
                "imageUrl": {
                    "type": "string",
                    "description": "The User's image as a url"
                },
                "imageBytes": {
                    "type": "string",
                    "description": "The User's image in a byteArray"
                },
                "followersCount" : {
                    "type" : "number",
                    "description" : "followers count"
                },
                "followingCount" : {
                    "type" : "number",
                    "description" : "following count"
                }
            }
        },
        "tweetText": {
            "type": "string",
            "description": "The User's tweet as text"
        },
        "timestamp": {
            "type": "number",
            "description": "Time of post"
        }
      }
    },
    "authToken": {
      "type": "string",
      "description": "the logged in user's authtoken"
    }
  }
}



// TODO: THIS IS A TEST JSON REQUEST
{
    "tweet": {
        "user": {
            "firstName": "hikaru",
            "lastName": "purba",
            "alias": "@hpurba",
            "imageUrl": "https://tweeteruserprofileimages.s3-us-west-2.amazonaws.com/@hpurba.png",
            "imageBytes": null,
            "followersCount" : 0,
            "followingCount" : 0
        },
        "tweetText": "Here is a post",
        "timestamp": 1618350537970
    },
    "authToken": "0455308b-62b8-488d-b2db-86991003cb7f"
}

 */

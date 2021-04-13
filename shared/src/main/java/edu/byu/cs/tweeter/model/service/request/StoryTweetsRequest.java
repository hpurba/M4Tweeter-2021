package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryTweetsRequest {

    private User user;
    private int limit;
    private Tweet lastTweet;
    private String authToken;


    public StoryTweetsRequest() {}

    /**
     * Creates an instance.
     *
     * @param user the {@link Tweet} whose tweet are to be returned.
     * @param limit the maximum number of followers to return.
     * @param user the last tweet that was returned in the previous request (null if
     *                     there was no previous request or if no followers were returned in the
     *                     previous request).
     */
    public StoryTweetsRequest(User user, int limit, Tweet lastTweet, String authToken) {
        this.user = user;
        this.limit = limit;
        this.lastTweet = lastTweet;
        this.authToken = authToken;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the number representing the maximum number of tweet to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last tweet that was returned in the previous request or null if there was no
     * previous request or if no followers were returned in the previous request.
     *
     * @return the last Tweet.
     */
    public Tweet getLastTweet() {
        return lastTweet;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastTweet(Tweet lastTweet) {
        this.lastTweet = lastTweet;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}


/*

{
  "title": "StoryTweetsRequest",
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
        }
      }
    },
    "limit": {
        "type": "number",
        "description": "The User's username"
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
  }
}
 */
package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class FollowingRequest {

    private User follower;
    private int limit;
    private User lastFollowee;
    private String authToken;

    public FollowingRequest() { }

    /**
     * Creates an instance.
     *
     * @param follower the {@link User} whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastFollowee the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */
    public FollowingRequest(User follower, int limit, User lastFollowee, String authToken) {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
        this.authToken = authToken;
    }

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public User getFollower() {
        return follower;
    }

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public User getLastFollowee() {
        return lastFollowee;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFollowee(User lastFollowee) {
        this.lastFollowee = lastFollowee;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}


// TODO: This can be used to test the lambda/API Gateway
/*

{
    "follower": {
        "firstname": "Hikaru",
        "lastname": "Purba",
        "alias": "@hpurba",
        "imageUrl": "https://tweeteruserprofileimages.s3-us-west-2.amazonaws.com/@hpurba.png",
        "imageBytes": null
    },
    "limit": 9,
    "lastFollowee": null,
    "authToken": "14ff3b9a-2563-4620-b9c9-35a7d5c72cb8"
}

 */


/*
// RESPONSE
{
  "success": true,
  "hasMorePages": false,
  "followees": [
    {
      "firstName": "Rei",
      "lastName": "Purba",
      "alias": "@reipurba",
      "imageUrl": "https://tweeteruserprofileimages.s3-us-west-2.amazonaws.com/%40reipurba.png",
      "followersCount": 0,
      "followingCount": 0,
      "name": "Rei Purba"
    }
  ]
}




 */




// TODO: THIS is the Model Schema

/*

{
  "title": "FollowingRequest",
  "type": "object",
  "properties": {
    "follower": {
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
      "type": "number"
    },
    "lastFollowee": {
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
    "authToken": {
      "type": "string",
      "description": "the logged in user's authtoken"
    }
  }
}

 */
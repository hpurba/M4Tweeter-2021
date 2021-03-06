package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowerRequest {

    private User followee;
    private int limit;
    private User lastFollower;
    private String authToken;

    public FollowerRequest() {};
    /**
     * Creates an instance.
     *
     * @param followee the {@link User} whose followers are to be returned.
     * @param limit the maximum number of followers to return.
     * @param lastFollower the last follower that was returned in the previous request (null if
     *                     there was no previous request or if no followers were returned in the
     *                     previous request).
     */
    public FollowerRequest(User followee, int limit, User lastFollower, String authToken) {
        this.followee = followee;
        this.limit = limit;
        this.lastFollower = lastFollower;
        this.authToken = authToken;
    }

    /**
     * Returns the followee whose followers are to be returned by this request.
     *
     * @return the followee.
     */
    public User getFollowee() {
        return followee;
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
     * Returns the last follower that was returned in the previous request or null if there was no
     * previous request or if no followers were returned in the previous request.
     *
     * @return the last follower.
     */
    public User getLastFollower() {
        return lastFollower;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFollower(User lastFollower) {
        this.lastFollower = lastFollower;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}




// TODO: THIS is the Model Schema

/*


{
  "title": "FollowerRequest",
  "type": "object",
  "properties": {
    "followee": {
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
      "type": "string"
    },
    "lastFollower": {
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
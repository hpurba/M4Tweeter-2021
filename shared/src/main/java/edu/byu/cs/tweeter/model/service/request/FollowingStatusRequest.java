package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowingStatusRequest {

    private User followerUser;
    private User followeeUser;
    private Boolean isFollowing;
    private String authToken;

    /**
     * Empty Default constructor
     */
    public FollowingStatusRequest() {}

    /**
     * Creates an instance.
     * @param followeeUser  User logged in. (unless another user is selected)
     * @param followerUser
     * @param isFollowing
     */
    public FollowingStatusRequest(User followeeUser, User followerUser, Boolean isFollowing) {
        this.followeeUser = followeeUser;
        this.followerUser = followerUser;
        this.isFollowing = isFollowing;
    }

    public User getFollowerUser() {
        return followerUser;
    }

    public void setFollowerUser(User followerUser) {
        this.followerUser = followerUser;
    }

    public User getFolloweeUser() {
        return followeeUser;
    }

    public void setFolloweeUser(User followeeUser) {
        this.followeeUser = followeeUser;
    }

    public Boolean getFollowing() {
        return isFollowing;
    }

    public void setFollowing(Boolean following) {
        isFollowing = following;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}



// TODO: THIS IS THE NEW SHCEMA
/*

{
  "title": "FollowingStatusRequest",
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
    "isFollowing": {
      "type": "boolean",
      "description":"Boolean that tells the app if user is following other user or not."
    },
    "authToken" : {
        "type": "string",
        "description" : "the authToken"
    }
  }
}


 */



// TODO: THIS IS THE OLD SCHEMA
/*

{
  "title": "FollowingStatusRequest",
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
    "myUsername": {
      "type": "string"
    },
    "otherPersonUsername": {
      "type": "string"
    },
    "isFollowing": {
      "type": "boolean"
    }
  }
}

 */
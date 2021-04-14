package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowingStatusRequest {

    private User user;
    private String otherUserAlias;
    private Boolean isFollowing;
    private String authToken;

    /**
     * Empty Default constructor
     */
    public FollowingStatusRequest() {}

    /**
     *
     * @param user
     * @param otherUserAlias
     * @param isFollowing
     * @param authToken
     */
    public FollowingStatusRequest(User user, String otherUserAlias, Boolean isFollowing, String authToken) {
        this.user = user;
        this.otherUserAlias = otherUserAlias;
        this.isFollowing = isFollowing;
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOtherUserAlias() {
        return otherUserAlias;
    }

    public void setOtherUserAlias(String otherUserAlias) {
        this.otherUserAlias = otherUserAlias;
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



// TODO: THis is a sample request for testing
/*

{
    "user": {
        "firstName": "Rocky",
        "lastName": "Purba",
        "alias": "@rpurba",
        "imageUrl": "https://tweeteruserprofileimages.s3-us-west-2.amazonaws.com/@rpurba.png",
        "imageBytes": null
    },
      "otherUserAlias": "@hpurba",
      "isFollowing": true,
      "authToken" : "005698fc-82ce-4b3d-b961-67e43c2dc207"
}

 */


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
    "otherUserAlias": {
      "type": "string",
      "description": "the other user's alias"
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
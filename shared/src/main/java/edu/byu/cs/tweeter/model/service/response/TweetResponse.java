package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class TweetResponse extends Response {
//
//    private String username;

    public TweetResponse(String message) {
        super(false, message);
    }

    public TweetResponse(Boolean success) {
        super(success);
    }

//    public TweetResponse(User user) {
//        super(true, null);
//        this.user = user;
//    }


//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}

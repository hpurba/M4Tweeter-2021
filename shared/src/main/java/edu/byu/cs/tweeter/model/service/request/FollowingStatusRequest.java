package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowingStatusRequest {

    private User user;
    private String myUsername;
    private String otherPersonUsername;
    private Boolean isFollowing;

    /**
     * Empty Default constructor
     */
    public FollowingStatusRequest() {}

    /**
     * Creates an instance.
     *
     * @param user
     * @param isFollowing
     */
    public FollowingStatusRequest(User user, Boolean isFollowing) {
        this.user = user;
        this.isFollowing = isFollowing;
    }

    public User getUser() {
        return user;
    }
    public String getMyUsername() {
        return myUsername;
    }
    public String getOtherPersonUsername() {
        return otherPersonUsername;
    }

    public Boolean getFollowing() {
        return isFollowing;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public void setOtherPersonUsername(String otherPersonUsername) {
        this.otherPersonUsername = otherPersonUsername;
    }

    public void setFollowing(Boolean following) {
        isFollowing = following;
    }
}

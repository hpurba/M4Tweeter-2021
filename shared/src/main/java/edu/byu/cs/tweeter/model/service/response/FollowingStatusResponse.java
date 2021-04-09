package edu.byu.cs.tweeter.model.service.response;

public class FollowingStatusResponse extends Response {

    private String user;
    private Boolean following; // isFollowing;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowingStatusResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user
     * @param isFollowing
     */
    public FollowingStatusResponse(String user, Boolean isFollowing) {
        super(true, null);
        this.user = user;
        this.following = isFollowing;
    }

    /**
     * Returns the logged in user.
     *
     * @return the user.
     */
    public String getUser() {
        return user;
    }

    public Boolean getFollowing() {
        return following;
    }

    public void updateUser(String user) {
        this.user = user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setFollowing(Boolean isFollowing) {
        following = isFollowing;
    }
}

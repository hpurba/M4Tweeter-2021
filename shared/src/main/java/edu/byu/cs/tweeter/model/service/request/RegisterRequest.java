package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String alias;
    private String password;
    public String byteArray;
    public String profileImageURL;
//    public byte[] byteArray;    // this should be a string.
    // Encode image in Base64 String and decode it on the back end.

    /**
     * Empty Default constructor
     */
    public RegisterRequest() { }

    public RegisterRequest(String firstName, String lastName, String alias, String password, String byteArray) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.password = password;
        this.byteArray = byteArray;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setByteArray(String byteArray) {
        this.byteArray = byteArray;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the username.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Returns the password of the user to be logged in by this request.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    public String getByteArray() {
        return byteArray;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }
}

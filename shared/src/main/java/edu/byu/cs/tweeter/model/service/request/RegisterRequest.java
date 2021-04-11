package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String alias;
    private String password;
    public byte[] byteArray;

    /**
     * Empty Default constructor
     */
    public RegisterRequest() { }

    public RegisterRequest(String firstName, String lastName, String alias, String password, byte[] byteArray) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.password = password;
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

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

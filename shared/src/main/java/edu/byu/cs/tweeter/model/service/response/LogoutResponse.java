package edu.byu.cs.tweeter.model.service.response;

public class LogoutResponse extends Response  {
    public LogoutResponse(boolean success) {
        super(success);
    }

    LogoutResponse(String message) {
        super(false, message);
    }

    public LogoutResponse() {
        super(true, null);
    }
}

package edu.byu.cs.tweeter.model.service.serviceInterfaces;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
// TODO: DELETE THIS WHEN WE KNOW THE LOGIN IS WORKING. WE MAY NOT NEED THIS.
public interface ILoginService {
    LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;
}

package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.serviceInterfaces.IRegisterService;

public class RegisterService extends BaseService implements IRegisterService {
    // The url_path extension for register. (Can be found in AWS console -> API:Tweeter -> Stages -> dev tab)
    private static final String URL_PATH = "/registeruser";

    // Register Response and Request Objects.
    RegisterResponse registerResponse;
    RegisterRequest registerRequest;

    /**
     * Performs the registering of a new user.
     *
     * @param request   RegisterRequest Object.
     * @return          RegisterResponse Object.
     * @throws IOException
     */
    public RegisterResponse register(RegisterRequest request) throws IOException {
        this.registerRequest = request;
        processServiceRequest();
        return registerResponse;
    }

    /**
     * This is the primary method in the Template pattern of the BaseService Class.
     * This will register a user by calling the register method in the server facade
     * by passing it the provided registerRequest (which is first passed into the register method).
     * Returning the registerResponse Object is handled in the register() method.
     *
     * @throws IOException
     * @throws TweeterRemoteException
     */
    @Override
    public void doServiceSpecificTask() throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        this.registerResponse = serverFacade.register(registerRequest, URL_PATH);
        if(registerResponse.isSuccess()) {
            loadImage(registerResponse.getUser());
        }
    }
}
package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

/**
 * The presenter for the register functionality of the application.
 */
public class RegisterPresenter {

    private final View view;

    /**
     * This is the interface for the presenter's view (RegisterFragment).
     * It is the interface by which this presenter can communicate with it's view (call it's methods).
     * Methods listed here should be used only for retrieval, or raising an event (ex: change a button status).
     *  - If needed, specify methods here that will be called on the view in response to model updates.
     */
    public interface View {
        String getFirstName();
        String getLastName();
        String getAlias();
        String getPassword();
//        byte[] getByteArray();
        String getByteArray();
    }

    /**
     * Creates an instance of a RegisterPresenter with the provided view (should be a RegisterFragment).
     * @param view View, which should be a RegisterFragment view for which this class is the presenter for.
     */
    public RegisterPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a register request.
     * @return  RegisterResponse Object.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    public RegisterResponse register() throws IOException {
        RegisterRequest registerRequest = new RegisterRequest(view.getFirstName(), view.getLastName(), view.getAlias(), view.getPassword(), view.getByteArray());
        RegisterService registerService = getRegisterService();
        return registerService.register(registerRequest);
    }

    /**
     * Performs a retrieval of a new RegisterService
     * @return  A new RegisterService Object.
     */
    RegisterService getRegisterService() {
        return new RegisterService();
    }
}

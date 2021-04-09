package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

/**
 * BaseService: Abstract Base Class for Other Services.
 *  - This follows the Template Pattern. "processServiceRequest" behaves as the skeleton
 *      implementation of the processing of a service request algorithm.
 */
abstract class BaseService {
    public ServerFacade serverFacade;

    /**
     * Sets the serverFacade by calling getServerFacade.
     */
    public final void setServerFacade()
    {
        serverFacade = getServerFacade();
    }

    /**
     * Returns an instance of {@link ServerFacade}.
     *  - Allows for mocking of the ServerFacade class during testing. All usages of ServerFacade
     *      should get their ServerFacade instance from this method to allow for proper mocking.
     *
     * @return a new instance of the ServerFacade.
     */
    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

    /**
     * Loads the profile image data for the user as a byte array.
     *
     * @param user  The user whose profile image data is to be loaded as a byte array.
     * @throws IOException
     */
    public void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    /**
     * Template
     * Gets the serverFacade and then does the service specific Task
     */
    public final void processServiceRequest( )
    {
        setServerFacade();
        try {
            doServiceSpecificTask();    // This will take care of the service specific task.
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the primary method in the Template pattern of the BaseService Abstract Class.
     * This is intended to be Overridden for each Service as part of the Template method pattern.
     * method Design Pattern.
     *
     * @throws IOException
     */
    public abstract void doServiceSpecificTask() throws IOException, TweeterRemoteException;
}
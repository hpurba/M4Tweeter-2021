package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.serviceInterfaces.IFollowingService;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceImpl extends ServiceImpl implements IFollowingService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowsDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    @Override
    public FollowingResponse getFollowees(FollowingRequest request) {
        checkAuthorizationToken(request.getAuthToken());
        return getFollowsDAO().getFollowees(request);
    }

    /**
     * Returns an instance of {@link FollowsDAO}. Allows mocking of the FollowsDAO class
     * for testing purposes. All usages of FollowsDAO should get their FollowsDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowsDAO getFollowsDAO() { return new FollowsDAO(); }
}

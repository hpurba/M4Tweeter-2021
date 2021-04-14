package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.server.service.FollowerServiceImpl;
import edu.byu.cs.tweeter.server.service.FollowingServiceImpl;

public class GetFollowersHandler implements RequestHandler<FollowerRequest, FollowerResponse> {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followees.
     */
    @Override
    public FollowerResponse handleRequest(FollowerRequest request, Context context) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400 : request is null");
        } else if (request.getLimit() < 0) {
            throw new RuntimeException("[BadRequest500] 500 : request limit cannot be -1");
        }
        FollowerServiceImpl service = new FollowerServiceImpl();
        return service.getFollowers(request);
    }
}
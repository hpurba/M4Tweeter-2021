package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.StoryTweetsRequest;
import edu.byu.cs.tweeter.model.service.response.StoryTweetsResponse;
import edu.byu.cs.tweeter.server.service.StoryTweetsServiceImpl;

public class StoryTweetsHandler implements RequestHandler<StoryTweetsRequest, StoryTweetsResponse> {
    @Override
    public StoryTweetsResponse handleRequest(StoryTweetsRequest request, Context context) {
        if (request == null) {
            throw new RuntimeException("[BadRequest400] 400 : request is null");
        } else if (request.getLimit() < 0) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        StoryTweetsServiceImpl service = new StoryTweetsServiceImpl();
        return service.getStoryTweets(request);
    }
}



// StoryTweetsRequest
/*



{
    "title": "StoryTweetsResponse",
    "type": "object",
    "properties": {
        "tweets": {
            "description": "The User's tweets",
            "type": "array",
            "items" : {
                "type": "object",
                "properties": {
                    "user": {
                        "type": "object",
                        "properties": {
                            "firstname": {
                                "type": "string",
                                "description": "The User's firstname"
                            },
                            "lastname": {
                                "type": "string",
                                "description": "The User's lastname"
                            },
                            "alias": {
                                "type": "string",
                                "description": "The User's alias"
                            },
                            "imageUrl": {
                                "type": "string",
                                "description": "The User's image as a url"
                            },
                            "imageBytes": {
                                "type": "string",
                                "description": "The User's image in a byteArray"
                            }
                        }
                    },
                    "tweetText": {
                        "type": "string",
                        "description": "The User's tweet as text"
                    },
                    "timestamp": {
                        "type": "number",
                        "description": "timestamp"
                    }
              }
            }
        }
    }
}

 */
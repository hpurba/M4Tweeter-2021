package edu.byu.cs.tweeter.model.net;

import com.google.gson.Gson;

/**
 * JSON Serializer will either serialize or deserialize.
 * Serialize will return an request object as a string.
 * Deserialize will return the response Object of the correct class with
 * the Response String and return class type.
 */
public class JsonSerializer {

    // Serialize Object to String. Used for making requests.
    public static String serialize(Object requestInfo) {
        return (new Gson()).toJson(requestInfo);
    }

    // Deserialize String to Object. Used for retrieving responses.
    public static <T> T deserialize(String value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }
}

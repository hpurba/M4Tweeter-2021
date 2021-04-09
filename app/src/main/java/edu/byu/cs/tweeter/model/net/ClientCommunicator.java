package edu.byu.cs.tweeter.model.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Client Communicator Class.
 * Uses the baseURL + requestURL extension to make an HTTP Connection.
 * doPost uses doRequest, and upon HTTP_OK, returns response as
 * "JsonSerializer.deserialize(responseString, returnType);"
 * which is the response Object.
 */
class ClientCommunicator {
    private static final int TIMEOUT_MILLIS = 10000;    // Connection timeout (10 sec. in milliseconds)
    private final String baseURL;                       // Base URL is the Invoke URL found in API:Tweeter stage:dev

    // Constructor for this ClientCommunicator
    ClientCommunicator(String baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * Interface: Strategy Pattern for Sending A Request.
     * - Set the Request Method using the HttpURLConnection
     * - Send the Request on the HttpURLConnection
     */
    private interface RequestStrategy {
        void setRequestMethod(HttpURLConnection connection) throws IOException;
        void sendRequest(HttpURLConnection connection) throws IOException;
    }

    /**
     *  Does an HTTP POST Request Method.
     *  1. Uses the provided HttpURLConnection to set the request method to be a: POST
     *  2. Send Request. Uses the Request Object (ex. LoginRequest). Serializes to JSON, writes its bytes to DataOutputStream.
     *
     *  Lastly, invoke the doRequest method using the specific urlPath for the API endpoint, request Object,
     *      header (usually null), and the Response object class type.
     *      - Note: doRequest will be the one that actually invokes the methods that are Overridden
     *          (setRequestMethod and sendRequest Method)
     *
     * @param urlPath       The urlPath is the extension to the baseURL. (ex: "/loginuser")
     * @param requestInfo   This is the request Object. (ex: LoginRequest)
     * @param headers       This is typically null.
     * @param returnType    Return type T: Generic class. T can be substituted with any Class name during initialization.
     * @param <T>           Class<T> represents a class object of specific class type 'T'.
     * @return              Returns return from doRequest method, which is a response object.
     * @throws IOException
     * @throws TweeterRemoteException
     */
    <T> T doPost(String urlPath, final Object requestInfo, Map<String, String> headers, Class<T> returnType)
            throws IOException, TweeterRemoteException {
        // Usage of the Request Strategy Interface. Defines the request strategy methods to be used in doRequest().
        RequestStrategy requestStrategy = new RequestStrategy() {
            @Override
            public void setRequestMethod(HttpURLConnection connection) throws IOException {
                connection.setRequestMethod("POST");
            }
            @Override
            public void sendRequest(HttpURLConnection connection) throws IOException {
                connection.setDoOutput(true);
                String entityBody = JsonSerializer.serialize(requestInfo);

                try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
                    os.writeBytes(entityBody);
                    os.flush();
                }
            }
        };
        // Makes the request. Upon success, it returns the result in the correct response object type.
        return doRequest(urlPath, headers, returnType, requestStrategy);
    }

    /**
     * TODO: Figure out when this will actually be used
     * https://www.w3schools.com/tags/ref_httpmethods.asp ->
     *  "Note that the query string (name/value pairs) is sent in the URL of a GET request"
     *
     *
     * @param urlPath       The urlPath is the extension to the baseURL. (ex: "/loginuser")
     * @param headers       This is typically null.
     * @param returnType    Return type T: Generic class. Returns appropriate Response Class object.
     * @param <T>           Class<T> represents a class object of specific class type 'T'.
     * @return
     * @throws IOException
     * @throws TweeterRemoteException
     */
    <T> T doGet(String urlPath, Map<String, String> headers, Class<T> returnType)
            throws IOException, TweeterRemoteException {
        // Usage of the RequestStrategy Interface. @Overrides default methods.
        RequestStrategy requestStrategy = new RequestStrategy() {
            @Override
            public void setRequestMethod(HttpURLConnection connection) throws IOException {
                connection.setRequestMethod("GET");
            }
            @Override
            public void sendRequest(HttpURLConnection connection) {
                // Nothing to send. For a get, the request is sent when the connection is opened.
                // TODO: Verify with a TA that this is left empty.
            }
        };
        return doRequest(urlPath, headers, returnType, requestStrategy);
    }

    /**
     * doRequest will Send the request with the HttpURLConnection.
     * On HTTP_OK response code, return the Response Object.
     *
     * @param urlPath
     * @param headers
     * @param returnType
     * @param requestStrategy
     * @param <T>
     * @return
     * @throws IOException
     * @throws TweeterRemoteException
     */
    private <T> T doRequest(String urlPath, Map<String, String> headers, Class<T> returnType, RequestStrategy requestStrategy)
            throws IOException, TweeterRemoteException {
        // Initialize the connection to null.
        HttpURLConnection connection = null;

        try {
            // Build the connection
            URL url = getUrl(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT_MILLIS);
            requestStrategy.setRequestMethod(connection);

            if(headers != null) {
                for (String headerKey : headers.keySet()) {
                    connection.setRequestProperty(headerKey, headers.get(headerKey));
                }
            }

            // SENDS THE REQUEST WITH THE CONNECTION
            requestStrategy.sendRequest(connection);

            // Depending on the responseCode...
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:
                    String responseString = getResponse(connection.getInputStream());
                    return JsonSerializer.deserialize(responseString, returnType);      // This is the working case we want to expect.
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    ErrorResponse errorResponse = getErrorResponse(connection);
                    throw new TweeterRequestException(errorResponse.errorMessage, errorResponse.errorType, errorResponse.stackTrace);
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    errorResponse = getErrorResponse(connection);
                    throw new TweeterServerException(errorResponse.errorMessage, errorResponse.errorType, errorResponse.stackTrace);
                default:
                    throw new RuntimeException("An unknown error occurred. Response code = " + connection.getResponseCode());
            }
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Produce the full url path from the baseURL and the urlPath extension.
     *
     * @param urlPath
     * @return
     * @throws MalformedURLException
     */
    private URL getUrl(String urlPath) throws MalformedURLException {
        String urlString = baseURL + (urlPath.startsWith("/") ? "" : "/") + urlPath;
        return new URL(urlString);
    }

    /**
     * getErrorResponse will handle errors from the connection.
     * If the responseString is null, no response from the server.
     * else, deserialize the responseString and returns it.
     *
     * @param connection The HttpURLConnection
     * @return ErrorResponse generated from the responseString.
     * @throws IOException
     */
    private ErrorResponse getErrorResponse(HttpURLConnection connection) throws IOException {
        String responseString = getResponse(connection.getErrorStream());
        if(responseString == null) {
            throw new RuntimeException("No response returned from server for response code " + connection.getResponseCode());
        } else {
            return JsonSerializer.deserialize(responseString, ErrorResponse.class);
        }
    }

    /**
     * Objective: Returns the response as a String from the inputStream.
     * Description: A BufferedReader is used to read each line of the inputStream.
     *              Each inputLine of the inputStream is appended to a response using a StringBuilder.
     *              Response is returned as a String.
     *
     * @param inputStream
     * @return response.toString() The response as a String (generated from the inputStream)
     * @throws IOException
     */
    private String getResponse(InputStream inputStream) throws IOException {
        if(inputStream == null)  {
            return null;
        } else {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {

                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                // Returns the response from the inputStream as a String.
                return response.toString();
            }
        }
    }

    /**
     * A class for de-serializing the json string the API Gateway returns with
     * a 400 or 500 status code.
     */
    @SuppressWarnings("unused")
    private static class ErrorResponse {
        private String errorMessage;
        private String errorType;
        private List<String> stackTrace;
    }
}
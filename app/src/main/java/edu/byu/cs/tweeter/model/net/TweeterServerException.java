package edu.byu.cs.tweeter.model.net;

import java.util.List;

/**
 * Tweeter Server Exception.
 */
public class TweeterServerException extends TweeterRemoteException {
    public TweeterServerException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}
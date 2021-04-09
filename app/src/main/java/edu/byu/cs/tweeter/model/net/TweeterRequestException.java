package edu.byu.cs.tweeter.model.net;

import java.util.List;

/**
 * Tweeter Request Exception.
 */
public class TweeterRequestException extends TweeterRemoteException {
    public TweeterRequestException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}
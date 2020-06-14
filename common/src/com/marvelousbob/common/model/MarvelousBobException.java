package com.marvelousbob.common.model;

public class MarvelousBobException extends RuntimeException {
    public MarvelousBobException() {
    }

    public MarvelousBobException(String message) {
        super(message);
    }

    public MarvelousBobException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarvelousBobException(Throwable cause) {
        super(cause);
    }

    public MarvelousBobException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

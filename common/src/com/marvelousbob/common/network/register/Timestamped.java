package com.marvelousbob.common.network.register;

public interface Timestamped {

    long getTimestamp();
    void setTimestamp(long timestammp);

    default void stampNow() {
        setTimestamp(System.currentTimeMillis());
    }
}

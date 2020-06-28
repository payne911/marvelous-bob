package com.marvelousbob.common.network.register;

import com.marvelousbob.common.utils.UUID;

public interface PlayerBroadcastable {

    UUID getSourcePlayerUuid();

    default boolean shouldBeIgnored(UUID currentPlayerUuid) {
        return getSourcePlayerUuid().equals(currentPlayerUuid);
    }
}

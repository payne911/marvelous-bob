package com.marvelousbob.common.model;

import com.marvelousbob.common.utils.UUID;

public interface Identifiable {
    UUID getUuid();

    default boolean isEquals(UUID other) {
        return getUuid().equals(other);
    }
}

package com.marvelousbob.common.model;


import com.marvelousbob.common.network.register.dto.UUID;

public interface Identifiable {
    UUID getUuid();

    default boolean isEquals(UUID other) {
        return getUuid().equals(other);
    }
}

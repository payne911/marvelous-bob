package com.marvelousbob.common.model;

import java.util.UUID;

public interface Identifiable {
    UUID getId();

    default boolean isEqulas(UUID other) {
       return getId().equals(other);
    }
}

package com.marvelousbob.common.utils;

import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Ultimately, for Hash structures, this class behaves just as if it was a Long.
 */
@RequiredArgsConstructor
public final class UUID {

    private static final AtomicLong COUNTER = new AtomicLong(0);

    @Getter
    private final long id;

    public static UUID getNext() {
        return new UUID(COUNTER.getAndIncrement());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        // todo: what if we wanted to compare with a Long directly? seems possible
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UUID uuid = (UUID) o;
        return id == uuid.getId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}

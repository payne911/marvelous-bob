package com.marvelousbob.common.utils;

import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Ultimately, for Hash structures, this class behaves just as if it was a Long.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class UUID {

    private static final AtomicLong COUNTER = new AtomicLong(0);

    @Getter
    private long id;

    public static UUID getNext() {
        return new UUID(COUNTER.getAndIncrement());
    }
}

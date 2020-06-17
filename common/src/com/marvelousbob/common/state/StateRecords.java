package com.marvelousbob.common.state;

import com.marvelousbob.common.network.register.dto.GameStateDto;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Contains a record of {@link GameStateDto} from the past up to the present.
 * <p>
 * Should be trimmed periodically to contain data up to 2 ticks in the past?
 * <p>
 * Must use a Concurrent data structure.
 * <p>
 * Should we accept Timestamps from player, or is that unsafe?
 * If we go for Timestamp as KEY, then we use
 * {@link java.util.concurrent.ConcurrentSkipListMap}
 * else if we go for ConnectionId as KEY, then we use
 * {@link java.util.concurrent.ConcurrentHashMap}
 * or {@link java.util.concurrent.ConcurrentLinkedQueue}
 * or {@link java.util.concurrent.ArrayBlockingQueue}
 * or {@link java.util.concurrent.LinkedBlockingQueue}
 * ?
 */
@Data
@Slf4j
public class StateRecords {

    /**
     * The {@link Long} is the Timestamp of the server's generated {@link GameStateDto}.
     */
    private ConcurrentNavigableMap<Long, GameStateDto> localRecord = new ConcurrentSkipListMap<>();

    /**
     * Overwrites an older value if it's the same key (timestamp).
     *
     * @param localGameState a game state generated locally, without regards to the server.
     * @see ConcurrentSkipListMap#put(Object, Object)
     */
    public void addLocalRecord(GameStateDto localGameState) {
        localRecord.put(localGameState.getTimestamp(), localGameState);

        log.debug("First timestamp in the SkipList: " + localRecord.firstKey());
        log.debug("First timestamp higher than last record (should be `null`): "
                + localRecord.higherKey(localGameState.getTimestamp()));
    }

    public void discardUpTo(Long timestamp) {
//        /* One possible way to do it. */
//        Long lowerKey = localRecord.lowerKey(timestamp);
//        while(lowerKey != null) {
//            localRecord.remove(lowerKey);
//            lowerKey = localRecord.lowerKey(timestamp);
//        }
//
//        /* Another possible way to do it. */
//        localRecord.headMap(timestamp).forEach((k,v) -> localRecord.remove(k));

        /* Yet some other solution. */
        localRecord.headMap(timestamp).clear(); // eventually consistent
    }
}

package com.marvelousbob.common.state;

import com.marvelousbob.common.network.register.dto.GameStateDto;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Contains a record of {@link GameStateDto} from the past up to the (almost) present. Keep in mind
 * that the present's Game State is <b><i>not</i></b> registered in the record.
 */
@Data
@Slf4j
public class GameStateRecords {

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

    /**
     * Will discard all the entries of the record which pre-date the input timestamp.
     *
     * @param timestamp timestamp of the {@link GameStateDto} received from the server
     * @return the last one removed (timestamp strictly inferior to input)
     */
    public GameStateDto discardUpTo(Long timestamp) {
        log.debug("Trying to discard up to timestamp: " + timestamp);
        Long lowerKey = localRecord.lowerKey(timestamp);
        log.debug("Lower key: " + lowerKey);
        GameStateDto lastRemoved = null;
        while (lowerKey != null) {
            lastRemoved = localRecord.remove(lowerKey);
            log.debug("Discarded following GS: " + lastRemoved);
            lowerKey = localRecord.lowerKey(timestamp);
        }
        return lastRemoved;
    }

    public Entry<Long, GameStateDto> getLastEntry() {
        return localRecord.lastEntry();
    }

    /**
     * Be aware that this will <b><i>not</i></b> return the <i>current</i> {@link GameStateDto}.
     *
     * @return the {@link GameStateDto} with the latest timestamp associated to it and which was
     * added to the record.
     */
    public GameStateDto getLastGameState() {
        return localRecord.lastEntry().getValue();
    }

    public Long getLastTimestamp() {
        return localRecord.lastKey();
    }

    public int getRecordSize() {
        return localRecord.size();
    }
}

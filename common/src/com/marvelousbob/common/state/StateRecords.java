package com.marvelousbob.common.state;

import com.marvelousbob.common.network.register.dto.GameState;
import lombok.Data;

/**
 * Contains a record of {@link GameState} from the past up to the present.
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
public class StateRecords {
}

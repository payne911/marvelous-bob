package com.marvelousbob.server.model.actions;

import com.marvelousbob.common.network.register.Timestamped;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.server.model.ServerState;

/**
 * For upating the server state with info that requires the delta
 */
public interface Action extends Timestamped, Comparable<Action> {

    void execute(final ServerState serverState, final float delta);

    long getIndex();

    UUID getPlayerId();

    @Override
    default int compareTo(Action o) {
        // TODO: 2020-06-21 Now, I think this is a bad idead...How to resolve slow connection with players? --OLA
        return Long.compare(getTimestamp(), o.getTimestamp());
    }
}

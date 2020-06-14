package com.marvelousbob.server.model.actions;

import com.marvelousbob.common.network.register.Timestamped;
import com.marvelousbob.server.model.ServerState;

public interface Action extends Timestamped, Comparable<Action> {

    void execute(final ServerState serverState);

    @Override
    default int compareTo(Action o) {
        return Long.compare(getTimestamp(), o.getTimestamp());
    }
}

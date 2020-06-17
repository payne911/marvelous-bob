package com.marvelousbob.client.controllers;

import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.state.StateRecords;

/**
 * Logic that relates to updating the world based on current and past data.
 */
public class GameStateUpdater {

    private StateRecords localStateRecords;

    public GameStateUpdater(StateRecords localStateRecords) {
        this.localStateRecords = localStateRecords;
    }

    /**
     * Discards all local game states up to the timestamp of the received game state. Then, since
     * the local player was assuming his actions were accepted, it now needs to ensure that the
     * server does not contradict his past actions. If the prediction was fine, nothing happens.
     * Else, a rewind needs to happen on the local machine.
     *
     * @param serverGameState a game state sent by the server, to be taken as the source of truth.
     */
    public void reconcile(GameStateDto serverGameState) {
        localStateRecords.discardUpTo(serverGameState.getTimestamp());
        // todo
    }
}

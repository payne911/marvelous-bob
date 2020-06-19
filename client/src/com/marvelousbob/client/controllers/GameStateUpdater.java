package com.marvelousbob.client.controllers;

import com.esotericsoftware.kryo.Kryo;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.state.GameStateRecords;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Logic that relates to updating the world based on current and past data.
 */
@Slf4j
public class GameStateUpdater {

    @Getter
    private final GameStateDto mutableCurrentLocalGameState;
    private final GameStateRecords localGameStateRecords;
    private final Kryo kryo; // used for deep copies

    public GameStateUpdater(GameStateRecords localGameStateRecords, Kryo kryo,
            GameStateDto initialGameState) {
        this.localGameStateRecords = localGameStateRecords;
        this.kryo = kryo;

        this.mutableCurrentLocalGameState = initialGameState;
        setUpInitialGameState(initialGameState);
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
        log.info("Reconciliation: before discard size = " + localGameStateRecords.getRecordSize());
        localGameStateRecords.discardUpTo(serverGameState.getTimestamp());
        log.info("Reconciliation: after discard size = " + localGameStateRecords.getRecordSize());

        registerCurrentLocalState();
        log.debug("Timestamp difference between current GS and reconciled server GS: "
                + (mutableCurrentLocalGameState.getTimestamp() - serverGameState.getTimestamp()));
        log.debug("Starting actual reconciliation with server GS: " + serverGameState);
        // todo: interpolate each GS chronologically until reaching current state

    }

    /**
     * Ensures it is a Deep Copy that is saved in the {@link GameStateRecords}.
     *
     * @param gameStateDto the GS to be saved as a copy.
     * @return the deep-copied version of the input.
     */
    public GameStateDto registerGameState(GameStateDto gameStateDto) {
        GameStateDto deepCopiedState = kryo.copy(gameStateDto);
        log.info("Registering the following GS copy: " + deepCopiedState);
        localGameStateRecords.addLocalRecord(deepCopiedState);
        return deepCopiedState;
    }

    /**
     * Adds a snapshot of the current state of the game into the {@link GameStateRecords}.
     *
     * @return the deep-copied version of the input.
     */
    public GameStateDto registerCurrentLocalState() {
        mutableCurrentLocalGameState.stampNow();
        return registerGameState(mutableCurrentLocalGameState);
    }

    /**
     * @param firstServerGameState The {@link GameStateDto} received from the server in the {@link
     *                             com.marvelousbob.client.network.listeners.GameInitializerListener}
     */
    public void setUpInitialGameState(GameStateDto firstServerGameState) {
        long serverTimestamp = firstServerGameState.getTimestamp();
        registerGameState(firstServerGameState); // keeping the timestamp from the server
        mutableCurrentLocalGameState.stampNow(); // counts as NOOP actions in between
        log.info("The timestamp difference between init GS and first local GS is: "
                + (mutableCurrentLocalGameState.getTimestamp() - serverTimestamp));
        registerGameState(mutableCurrentLocalGameState);
    }
}

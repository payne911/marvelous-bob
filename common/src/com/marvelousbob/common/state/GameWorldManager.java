package com.marvelousbob.common.state;

import com.esotericsoftware.kryo.Kryo;
import com.marvelousbob.common.model.entities.GameWorld;
import com.marvelousbob.common.model.entities.Level;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.utils.MovementUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Logic that relates to updating the world based on current and past data.
 */
@Slf4j
public class GameWorldManager {

    private long newestGameStateIndexProcessed;

    @Getter
    private final LocalGameState mutableLocalGameState;

    @Getter
    private final Level mutableCurrentLevel;

    /**
     * The Rendering thread always renders this (through concurrent access).
     */
    @Getter
    private final GameWorld mutableGameWorld;

    /**
     * Used for deep copies.
     */
    private final Kryo kryo;


    public GameWorldManager(Kryo kryo, GameWorld initialGameWorld) {
        this.kryo = kryo;
        this.mutableGameWorld = initialGameWorld;
        this.mutableLocalGameState = initialGameWorld.getLocalGameState();
        this.mutableCurrentLevel = initialGameWorld.getLevel();
    }

    /**
     * Ensures the client synchronizes the latest server updates.
     *
     * @param serverGameState a game state sent by the server, to be taken as the source of truth.
     */
    public void reconcile(GameStateDto serverGameState) {
        log.debug("Starting actual reconciliation with server GS: " + serverGameState);

        if (serverGameState.getIndex() > newestGameStateIndexProcessed) {
            newestGameStateIndexProcessed = serverGameState.getIndex();
            serverGameState.playerUpdates.forEach(mutableLocalGameState::updateUsingPlayerList);
            serverGameState.basesHealth.forEach(mutableCurrentLevel::updateUsingPlayerBase);
            serverGameState.spawnPointHealth.forEach(mutableCurrentLevel::updateUsingSpawnPoints);
        } else {
            log.warn("Received an older GS than the last one processed.");
        }
        serverGameState.enemyCollisions.forEach(mutableLocalGameState::updateUsingEnemyCollision);
        serverGameState.newEnemies.forEach(mutableLocalGameState::updateUsingNewEnemyList);
    }

    /**
     * Used to update the internal state of the GS. Things such as movement.
     *
     * @param delta amount of time which has passed since last render.
     */
    public void updateGameState(float delta) {
        MovementUtils.interpolatePlayers(mutableLocalGameState.getPlayers().values(), delta);
    }
}

package com.marvelousbob.common.state;

import com.marvelousbob.common.model.entities.GameWorld;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Logic that relates to updating the world based on current and past data.
 */
@Slf4j
public class GameWorldManager {

    private long newestGameStateIndexProcessed;


    /**
     * The Rendering thread always renders this (through concurrent access).
     */
    @Getter
    @Setter
    private GameWorld mutableGameWorld;


    public GameWorldManager(GameWorld initialGameWorld) {
        this.mutableGameWorld = initialGameWorld;
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
            serverGameState.playerUpdates.forEach(mutableGameWorld::updatePlayer);
            serverGameState.basesHealth.forEach(mutableGameWorld::updatePlayerBase);
            serverGameState.spawnPointHealth.forEach(mutableGameWorld::updateSpawnPoints);
        } else {
            log.warn("Received an older GS than the last one processed.");
        }
        serverGameState.enemyCollisions.forEach(mutableGameWorld::updateEnemyCollision);
        serverGameState.newEnemies.forEach(mutableGameWorld::updateNewEnemy);
    }

    /**
     * Used to update the internal state of the GS. Things such as movement.
     *
     * @param delta amount of time which has passed since last render.
     */
    public void updateGameState(float delta) {
        mutableGameWorld.interpolatePlayerPositions(delta);
    }
}

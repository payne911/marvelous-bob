package com.marvelousbob.common.state;

import com.marvelousbob.common.model.entities.dynamic.allies.Player;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Logic that relates to updating the world based on current and past data.
 */
@Slf4j
public abstract class GameWorldManager {

    private long newestGameStateIndexProcessed;


    /**
     * The Rendering thread always renders this (through concurrent access).
     */
    @Getter
    @Setter
    protected GameWorld mutableGameWorld;


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
     * @param delta amount of time which has passed since last render
     */
    public abstract void updateGameState(float delta);

    /**
     * Used by both Client and Server to update the internal state of the GS. Things such as
     * movement.
     *
     * @param delta amount of time which has passed since last render.
     */
    protected void commonGameStateUpdate(float delta) {
        mutableGameWorld.checkForPlayerCollisionWithWalls();
        mutableGameWorld.moveEntities(delta);
    }

    public void updatePlayerDestination(Player player, float destX, float destY) {
        player.getDestination().x = destX;
        player.getDestination().y = destY;

//        An attempt at wall teleportation fix
//        Vector2 dest = new Vector2(destX, destY);
//        mutableGameWorld.checkForLineIntersectionWithAllWalls(player.getCurrCenterPos(), dest)
//                .ifPresentOrElse(v -> {
//                    log.debug("Wall detected between player and destination");
//                    if (player.getCurrCenterX() < v.x) {
//                        player.getDestination().x = v.x - player.getHalfSize() - 1;
//                    } else if (player.getCurrCenterX() > v.x) {
//                        player.getDestination().x = v.x + player.getHalfSize() + 1;
//                    }
//                    if (player.getCurrCenterY() < v.y) {
//                        player.getDestination().y = v.y - player.getHalfSize() - 1;
//                    } else if (player.getCurrCenterY() > v.y) {
//                        player.getDestination().y = v.y + player.getHalfSize() + 1;
//                    }
//                },
//                () -> {
//                    player.getDestination().x = dest.x;
//                    player.getDestination().y = dest.y;
//                });
    }
}

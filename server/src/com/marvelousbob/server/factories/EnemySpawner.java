package com.marvelousbob.server.factories;

import com.marvelousbob.common.model.entities.dynamic.enemies.Enemy;
import com.marvelousbob.common.model.entities.dynamic.enemies.PolygonEnemy;
import com.marvelousbob.common.model.entities.level.EnemySpawnPoint;
import com.marvelousbob.common.state.LocalGameState;
import com.marvelousbob.common.utils.UUID;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

/**
 * Manages the {@link com.marvelousbob.common.model.entities.level.EnemySpawnPoint} of a single
 * {@link com.marvelousbob.common.model.entities.GameWorld}'s {@link LocalGameState}.
 */
@Slf4j
public class EnemySpawner {

    private final ArrayList<Enemy> spawnedEnemies = new ArrayList<>(); // optimization to prevent frequent instantiation
    private final LocalGameState gameState;

    public EnemySpawner(LocalGameState gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Enemy> update(float delta, Iterable<EnemySpawnPoint> spawnPoints) {
        spawnedEnemies.clear();
        spawnPoints.forEach(sp -> {
            boolean shouldSpawn = sp.update(delta);
            if (shouldSpawn) {
                log.info("Should spawn for {}", sp);
                var enemy = gameState.addEnemy(
                        new PolygonEnemy(UUID.getNext(), sp.getUuid(), sp.getPos().cpy()));
                spawnedEnemies.add(enemy);
            }
        });
        return spawnedEnemies;
    }
}

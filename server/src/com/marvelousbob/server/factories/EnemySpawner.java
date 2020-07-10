package com.marvelousbob.server.factories;

import com.badlogic.gdx.math.MathUtils;
import com.marvelousbob.common.model.entities.dynamic.enemies.CircleEnemy;
import com.marvelousbob.common.model.entities.dynamic.enemies.Enemy;
import com.marvelousbob.common.model.entities.dynamic.enemies.PolygonEnemy;
import com.marvelousbob.common.model.entities.level.EnemySpawnPoint;
import com.marvelousbob.common.state.GameWorld;
import com.marvelousbob.common.state.LocalGameState;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.common.utils.movements.PathMovement;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

/**
 * Manages the {@link com.marvelousbob.common.model.entities.level.EnemySpawnPoint} of a single
 * {@link GameWorld}'s {@link LocalGameState}.
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
                log.info("Should spawn for EnemySpawnPoint {}, pos={}, spawnRate={}",
                        sp.getUuid(), sp.getPos(), sp.getSpawnRate());

                var path = PathMovement.from(sp.getPathsToBase().get(0)); // todo: assumes 1 Base
                var randomEnemy = MathUtils.randomBoolean()
                        ? new PolygonEnemy(UUID.getNext(), sp.getUuid(), path, sp.getPos().cpy())
                        : new CircleEnemy(UUID.getNext(), sp.getUuid(), path, sp.getPos().cpy(), 7);

                var enemy = gameState.addEnemy(randomEnemy);
                spawnedEnemies.add(enemy);
            }
        });
        return spawnedEnemies;
    }
}

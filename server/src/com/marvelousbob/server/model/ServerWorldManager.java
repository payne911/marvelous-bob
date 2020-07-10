package com.marvelousbob.server.model;

import com.marvelousbob.common.model.entities.dynamic.enemies.Enemy;
import com.marvelousbob.common.state.GameWorld;
import com.marvelousbob.common.state.GameWorldManager;
import com.marvelousbob.server.factories.EnemySpawner;
import java.util.ArrayList;
import lombok.Getter;

public class ServerWorldManager extends GameWorldManager {

    @Getter
    private final EnemySpawner enemySpawner;

    @Getter
    private final ArrayList<Enemy> spawnedEnemies = new ArrayList<>();

    public ServerWorldManager(GameWorld initialGameWorld) {
        super(initialGameWorld);
        this.enemySpawner = new EnemySpawner(initialGameWorld.getLocalGameState());
    }

    @Override
    public void updateGameState(float delta) {
        spawnEnemies(delta);
        commonGameStateUpdate(delta);
    }

    private void spawnEnemies(float delta) {
        var spawned = enemySpawner.update(delta, mutableGameWorld.getLevel().getAllSpawnPoints());
        spawnedEnemies.addAll(spawned);
    }

    public ArrayList<Enemy> extractNewEnemies() {
        ArrayList<Enemy> returnedList = new ArrayList<>(spawnedEnemies);
        spawnedEnemies.clear();
        return returnedList;
    }
}

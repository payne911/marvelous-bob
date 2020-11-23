package com.marvelousbob.server.model;

import com.marvelousbob.common.model.entities.dynamic.enemies.Enemy;
import com.marvelousbob.common.network.register.dto.PlayersBaseDto;
import com.marvelousbob.common.state.GameWorld;
import com.marvelousbob.common.state.GameWorldManager;
import com.marvelousbob.server.factories.EnemySpawner;
import java.util.ArrayList;
import java.util.stream.Collectors;
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

    public ArrayList<PlayersBaseDto> updateBases() {
        if (mutableGameWorld.getLevel() == null) {
            return new ArrayList<>();
        }

        var bases = mutableGameWorld.getLevel().getAllPlayerBases();
        var enemies = mutableGameWorld.getLocalGameState().getEnemiesList();

        ArrayList<PlayersBaseDto> playersBaseDtos = new ArrayList<>();
        bases.forEach(base -> {
            var collidedUuids = enemies.stream()
                    .filter(enemy -> enemy.collidesWith(base.getInnerCircle()))
                    .map(enemy -> {
                        var uuid = enemy.getUuid();
                        mutableGameWorld.getLocalGameState().removeEnemy(uuid); // side-effect
                        return uuid;
                    })
                    .collect(Collectors.toList());
            if (!collidedUuids.isEmpty()) {
                var newDto = new PlayersBaseDto();
                newDto.setEnemiesToRemove(collidedUuids);
                newDto.hp = base.getHp();
                newDto.uuid = base.getUuid();
                playersBaseDtos.add(newDto);
            }
        });

        return playersBaseDtos;
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

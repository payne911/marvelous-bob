package com.marvelousbob.server.model;

import com.marvelousbob.common.model.entities.dynamic.enemies.Enemy;
import com.marvelousbob.common.network.register.dto.PlayersBaseDto;
import com.marvelousbob.common.state.GameWorld;
import com.marvelousbob.common.state.GameWorldManager;
import com.marvelousbob.server.factories.EnemySpawner;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            final float[] DMG_TO_BASE = new float[]{0};
            var collidedUuids = enemies.stream()
                    .filter(enemy -> enemy.collidesWith(base.getInnerCircle()))
                    .map(enemy -> {
                        var uuid = enemy.getUuid();
                        DMG_TO_BASE[0] += enemy.getDamage();
                        mutableGameWorld.getLocalGameState().removeEnemy(uuid); // side-effect
                        return uuid;
                    })
                    .collect(Collectors.toList());
            if (!collidedUuids.isEmpty()) {
                var newDto = new PlayersBaseDto();
                newDto.setEnemiesToRemove(collidedUuids);

                var newHp = base.getHp() - DMG_TO_BASE[0];
                base.setHp(newHp);
                newDto.hp = newHp;
                newDto.uuid = base.getUuid();
                playersBaseDtos.add(newDto);

                if (newDto.hp <= 0) {
                    // todo: clear level, generate new one (defeat)
                }
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

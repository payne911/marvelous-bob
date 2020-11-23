package com.marvelousbob.client.controllers;

import com.marvelousbob.common.model.entities.dynamic.allies.RangedPlayer;
import com.marvelousbob.common.state.GameWorld;
import com.marvelousbob.common.state.GameWorldManager;

public class ClientWorldManager extends GameWorldManager {


    public ClientWorldManager(GameWorld initialGameWorld) {
        super(initialGameWorld);
    }

    @Override
    public void updateGameState(float delta) {
        commonGameStateUpdate(delta);
        animateBases(delta);
        checkBulletsCollisions();
    }

    private void animateBases(float delta) {
        mutableGameWorld.getLevel().getAllPlayerBases().forEach(b -> b.update(delta));
    }

    private void checkBulletsCollisions() {
        var enemies = mutableGameWorld.getLocalGameState().getEnemiesList();
        mutableGameWorld.getLocalGameState().getPlayersList().stream()
                .filter(player -> player instanceof RangedPlayer)
                .forEach(player -> ((RangedPlayer) player).getBulletsList()
                        .forEach(bullet -> enemies.stream()
                                .filter(enemy -> enemy.collidesWith(bullet.getCircle()))
                                .map(enemy -> {
                                    ((RangedPlayer) player).explodeBullet(bullet);
                                    return enemy.getUuid();
                                })
                                .forEach(uuid -> {
                                    mutableGameWorld.getLocalGameState().removeEnemy(uuid);

                                    // todo: notify server that bullet+enemy are to be destroyed
                                    //MyGame.client.sendTCP();
                                })));
    }
}

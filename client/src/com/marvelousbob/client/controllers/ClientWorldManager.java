package com.marvelousbob.client.controllers;

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
    }

    private void animateBases(float delta) {
        mutableGameWorld.getLevel().getAllPlayerBases().forEach(b -> b.update(delta));
    }
}

package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.MyGame;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.IndexedGameStateDto;


public class GameStateListener extends AbstractListener<IndexedGameStateDto> {

    public GameStateListener() {
        super(IndexedGameStateDto.class);
    }

    @Override
    public void accept(Connection connection, IndexedGameStateDto gameStateDto) {
        if (MyGame.controller != null) {
            MyGame.controller.getGameWorldManager().reconcile(gameStateDto);
        }
    }
}

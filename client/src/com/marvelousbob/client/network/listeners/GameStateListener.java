package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.MyGame;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameStateDto;


public class GameStateListener extends AbstractListener<GameStateDto> {

    public GameStateListener() {
        super(GameStateDto.class);
    }

    @Override
    public void accept(Connection connection, GameStateDto gameStateDto) {
        if (MyGame.controller != null) {
            MyGame.controller.getClientWorldManager().reconcile(gameStateDto);
        }
    }
}

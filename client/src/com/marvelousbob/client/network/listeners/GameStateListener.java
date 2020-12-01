package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.MyGame;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.utils.UUID;


public class GameStateListener extends AbstractListener<GameStateDto> {

    private final UUID selfUuid;

    public GameStateListener(UUID selfPlayerUuid) {
        super(GameStateDto.class);
        this.selfUuid = selfPlayerUuid;
    }

    @Override
    public void accept(Connection connection, GameStateDto gameStateDto) {
        if (MyGame.controller != null) {
            MyGame.controller.getClientWorldManager().reconcile(gameStateDto, selfUuid);
        }
    }
}

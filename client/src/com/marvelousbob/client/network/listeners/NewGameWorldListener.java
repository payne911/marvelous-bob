package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.NewGameWorldDto;
import com.marvelousbob.common.state.GameWorldManager;

public class NewGameWorldListener extends AbstractListener<NewGameWorldDto> {

    private final GameWorldManager gameWorldManager;

    public NewGameWorldListener(GameWorldManager gameWorldManager) {
        super(NewGameWorldDto.class);
        this.gameWorldManager = gameWorldManager;
    }

    @Override
    public void accept(Connection connection, NewGameWorldDto newGameWorldDto) {
        // todo: probably other stuff to be done to ensure no last-minute receptions of Dtos concerning the older DTO are treated
        gameWorldManager.setMutableGameWorld(newGameWorldDto.newGameWorld);
    }
}

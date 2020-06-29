package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.PlayerDisconnectionDto;
import com.marvelousbob.common.state.LocalGameState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerDisconnectListener extends AbstractListener<PlayerDisconnectionDto> {

    private final LocalGameState localGameState;

    public PlayerDisconnectListener(LocalGameState localGameState) {
        super(PlayerDisconnectionDto.class);
        this.localGameState = localGameState;
    }

    @Override
    public void accept(Connection connection, PlayerDisconnectionDto playerDisconnectionDto) {
        log.info("Player disconnected: " + playerDisconnectionDto.getPlayerUuid());
        localGameState.removePlayer(playerDisconnectionDto.getPlayerUuid());
    }
}

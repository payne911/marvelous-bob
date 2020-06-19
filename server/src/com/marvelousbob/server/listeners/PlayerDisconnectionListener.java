package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.PlayerDisconnectionDto;

/**
 * On the Client-side, the GameState is used to determine if the player has left: no need for
 * sending a specific Event.
 */
public class PlayerDisconnectionListener extends AbstractListener<PlayerDisconnectionDto> {

    private final GameStateDto currentGameStateDto;

    public PlayerDisconnectionListener(GameStateDto currentGameStateDto) {
        super(PlayerDisconnectionDto.class);
        this.currentGameStateDto = currentGameStateDto;
    }

    @Override
    public void accept(Connection connection, PlayerDisconnectionDto elem) {
        currentGameStateDto.removePlayer(elem.getPlayerId());
        connection.close();
    }
}

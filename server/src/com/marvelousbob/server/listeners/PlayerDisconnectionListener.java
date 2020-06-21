package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.PlayerDisconnectionDto;
import com.marvelousbob.server.model.ServerState;

/**
 * On the Client-side, the GameState is used to determine if the player has left: no need for
 * sending a specific Event.
 */
public class PlayerDisconnectionListener extends AbstractListener<PlayerDisconnectionDto> {

    private final ServerState serverState;

    public PlayerDisconnectionListener(ServerState serverState) {
        super(PlayerDisconnectionDto.class);
        this.serverState = serverState;
    }

    @Override
    public void accept(Connection connection, PlayerDisconnectionDto elem) {
        serverState.removePlayer(elem.getPlayerId());
        connection.close();
    }
}

package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.PlayerDisconnectionDto;
import com.marvelousbob.server.model.ServerState;
import lombok.extern.slf4j.Slf4j;

/**
 * On the Client-side, the GameState is used to determine if the player has left: no need for
 * sending a specific Event.
 */
@Slf4j
public class PlayerDisconnectionListener extends AbstractListener<PlayerDisconnectionDto> {

    private final Server server;
    private final ServerState serverState;

    public PlayerDisconnectionListener(Server server, ServerState serverState) {
        super(PlayerDisconnectionDto.class);
        this.serverState = serverState;
        this.server = server;
    }

    @Override
    public void accept(Connection connection, PlayerDisconnectionDto elem) {
        log.warn("Disconnection of player UUID: " + elem.getPlayerUuid());
        connection.close();

        if (serverState.hasOnePlayer()) {
            log.warn("Last player in the Room has just disconnected: ServerState#completeReset()");
            serverState.completeReset();
        } else {
            serverState.freePlayerColorId(elem.getPlayerUuid());
            serverState.removePlayer(elem.getPlayerUuid());

            server.sendToAllExceptTCP(connection.getID(),
                    new PlayerDisconnectionDto(elem.getPlayerUuid()));
        }
    }
}

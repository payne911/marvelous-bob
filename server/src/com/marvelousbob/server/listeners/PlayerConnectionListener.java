package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.model.entities.dynamic.allies.Player;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.PlayerConnectionDto;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.server.model.ServerState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerConnectionListener extends AbstractListener<PlayerConnectionDto> {

    private final Server server;
    private final ServerState serverState;

    public PlayerConnectionListener(Server server, ServerState serverState) {
        super(PlayerConnectionDto.class);
        this.server = server;
        this.serverState = serverState;
    }

    @Override
    public void accept(Connection connection, PlayerConnectionDto playerConnection) {
        if (serverState.isEmptyRoom()) {
            serverState.initializeOnFirstPlayerConnected();
        }
        UUID uuid = UUID.getNext();
        Player<?> player = null;
        try {
            player = playerConnection.playerType
                    .getPlayerInstance(uuid, serverState.getFreeColor(uuid),
                            serverState.getServerWorldManager().getMutableGameWorld().randomPos());
            serverState.addPlayer(player);
            server.sendToTCP(connection.getID(), serverState.getGameInitDto(uuid));
            server.sendToAllExceptTCP(connection.getID(), player);
        } catch (MarvelousBobException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            // todo: send Connection Refused
        }
    }
}

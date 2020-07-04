package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.model.entities.dynamic.allies.Player;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.ForceNewLevelDto;
import com.marvelousbob.server.model.ServerState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ForceNewLevelListener extends AbstractListener<ForceNewLevelDto> {

    private final ServerState serverState;
    private final Server server;

    public ForceNewLevelListener(Server server, ServerState serverState) {
        super(ForceNewLevelDto.class);
        this.serverState = serverState;
        this.server = server;
    }

    @Override
    public void accept(Connection connection, ForceNewLevelDto forceNewLevelDto) {
        synchronized (serverState) {
            serverState.newLevel();
            serverState.getPlayers().stream()
                    .map(Player::getUuid)
                    .forEach(uuid -> server.sendToAllTCP(serverState.getGameInitDto(uuid)));
        }
    }
}

package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.server.model.ServerState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveActionListener extends AbstractListener<MoveActionDto> {

    private final ServerState serverState;
    private final Server server;

    public MoveActionListener(Server server, ServerState serverState) {
        super(MoveActionDto.class);
        this.serverState = serverState;
        this.server = server;
    }

    @Override
    public void accept(Connection connection, MoveActionDto moveActionDto) {
        log.debug("Received MoveAction: " + moveActionDto);
        serverState.updatePlayerPos(moveActionDto);
        server.sendToAllTCP(moveActionDto);
    }
}

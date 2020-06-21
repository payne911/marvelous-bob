package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.IndexedMoveActionDto;
import com.marvelousbob.server.model.ServerState;
import com.marvelousbob.server.model.actions.MoveAction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveActionListener extends AbstractListener<IndexedMoveActionDto> {

    private final ServerState serverState;

    public MoveActionListener(ServerState serverState) {
        super(IndexedMoveActionDto.class);
        this.serverState = serverState;
    }


    @Override
    public void accept(Connection connection, IndexedMoveActionDto moveActionDto) {
        log.debug("Received MoveAction: " + moveActionDto);
        serverState.addAction(new MoveAction(moveActionDto));
    }
}

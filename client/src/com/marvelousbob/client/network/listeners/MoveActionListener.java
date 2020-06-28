package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.state.LocalGameState;
import com.marvelousbob.common.utils.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveActionListener extends AbstractListener<MoveActionDto> {

    private final LocalGameState localGameState;
    private final UUID selfPlayerUuid;

    public MoveActionListener(LocalGameState localGameState, UUID selfPlayerUuid) {
        super(MoveActionDto.class);
        this.localGameState = localGameState;
        this.selfPlayerUuid = selfPlayerUuid;
    }

    @Override
    public void accept(Connection connection, MoveActionDto moveDto) {
        log.info("MoveActionListener received: " + moveDto);
        if (moveDto.shouldBeIgnored(selfPlayerUuid)) {
            log.warn("Ignoring the moveAction");
            return;
        }

        localGameState.updateUsingMoveAction(moveDto);
    }
}

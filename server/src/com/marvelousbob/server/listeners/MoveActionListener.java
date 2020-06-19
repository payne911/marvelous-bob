package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveActionListener extends AbstractListener<MoveActionDto> {

    private final GameStateDto gameState;

    public MoveActionListener(GameStateDto gameState) {
        super(MoveActionDto.class);
        this.gameState = gameState;
    }

    @Override
    public void accept(Connection connection, MoveActionDto moveActionDto) {
        log.debug("Received MoveAction: " + moveActionDto);
        log.debug("Current GameState before changing 'dest' values: " + gameState.toString());
        for (PlayerDto p : gameState.getPlayersDtos()) { // todo: create new State and THEN update
            if (moveActionDto.getPlayerId().equals(p.getUuid())) {
                log.info("Move action for player %s detected".formatted(p.getUuid().getStringId()));
                p.setDestX(moveActionDto.getDestX());
                p.setDestY(moveActionDto.getDestY());
                break;
            }
        }
        connection.sendTCP(gameState);
    }
}

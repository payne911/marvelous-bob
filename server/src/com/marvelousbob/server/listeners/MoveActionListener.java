package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;

public class MoveActionListener extends AbstractListener<MoveActionDto> {

    private GameStateDto gameState;

    public MoveActionListener(GameStateDto gameState) {
        super(MoveActionDto.class);
        this.gameState = gameState;
    }

    @Override
    public void accept(Connection connection, MoveActionDto moveActionDto) {
        for (PlayerDto p : gameState.getPlayerDtos()) {
            if (moveActionDto.getPlayerId().equals(p.getId())) {
                p.setDestX(moveActionDto.getDestX());
                p.setDestY(moveActionDto.getDestY());
            }
            break;
        }
        connection.sendTCP(gameState);
    }
}

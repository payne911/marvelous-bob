package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.PlayerDisconnectionDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;

public class PlayerDisconnectionListener extends AbstractListener<PlayerDisconnectionDto> {

    private final GameStateDto gameStateDto;

    public PlayerDisconnectionListener(GameStateDto gameStateDto) {
        super(PlayerDisconnectionDto.class);
        this.gameStateDto = gameStateDto;
    }

    @Override
    public void accept(Connection conncetion, PlayerDisconnectionDto elem) {
        for (PlayerDto p : gameStateDto.getPlayersDtos()) {
            if (elem.getPlayerId().equals(p.getUuid())) {
                gameStateDto.getPlayersDtos().remove(p);
                break;
            }
        }
        conncetion.close();
    }
}

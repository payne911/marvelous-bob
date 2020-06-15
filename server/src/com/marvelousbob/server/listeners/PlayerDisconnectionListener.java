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
        for (PlayerDto p : gameStateDto.getPlayerDtos()) {
            if (elem.getPlayerId().equals(p.getId())) {
                gameStateDto.getPlayerDtos().remove(p);
                break;
            }
        }
        conncetion.close();
    }
}

package com.marvelousbob.server.model.actions;

import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.network.register.dto.UUID;
import com.marvelousbob.server.model.ServerState;
import lombok.Getter;
import lombok.Setter;


public class MoveAction implements Action {

    @Getter
    @Setter
    private long timestamp;
    private UUID playerId;
    private float deltaX, deltaY;

    public MoveAction(MoveActionDto dto) {
        this.timestamp = dto.getTimestamp();
        this.playerId = dto.getPlayerId();
        this.deltaX = dto.getDestX();
        this.deltaY = dto.getDestY();
    }


    @Override
    public void execute(final ServerState serverState) {
        serverState.getPlayer(playerId).ifPresent(this::updatePlayerPos);
    }


    private void updatePlayerPos(PlayerDto playerDto) {
        playerDto.setDestX(playerDto.getCurrX() + deltaX);
        playerDto.setDestY(playerDto.getCurrY() + deltaY);
    }
}

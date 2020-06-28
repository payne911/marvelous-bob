package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.MarvelousBob;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.MoveActionDto;

public class MoveActionListener extends AbstractListener<MoveActionDto> {

    private final MarvelousBob marvelousBob;

    public MoveActionListener(MarvelousBob marvelousBob) {
        super(MoveActionDto.class);
        this.marvelousBob = marvelousBob;
    }

    @Override
    public void accept(Connection connection, MoveActionDto moveDto) {
        if (moveDto.shouldBeIgnored(
                marvelousBob.getGameScreen().getController().getSelfPlayer().getUuid())) {
            return;
        }

        if (lastTimestampObserved > moveDto.timestamp) {
            return;
        }

        lastTimestampObserved = moveDto.timestamp;
        marvelousBob.getGameScreen().getController().getGameWorld().getLocalGameState()
                .getPlayer(moveDto.getSourcePlayerUuid()).ifPresent(p -> {
            p.setDestX(moveDto.getDestX());
            p.setDestY(moveDto.getDestY());
        });
    }
}

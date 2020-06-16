package com.marvelousbob.client.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.network.register.dto.UUID;

public class Player extends Actor implements Identifiable {

    private PlayerDto dto;

    public Player(PlayerDto playerDto) {
        this.dto = playerDto;
    }

    public void adjustToDto(PlayerDto playerDto) {

    }

    @Override
    public UUID getUuid() {
        return dto.getUuid();
    }
}

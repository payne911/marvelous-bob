package com.marvelousbob.client.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import lombok.experimental.Delegate;

public class Player extends Actor implements Identifiable {

    @Delegate
    private PlayerDto dto;

    public Player(PlayerDto playerDto) {
        this.dto = playerDto;
        updateFromDto(playerDto);
    }

    public void updateFromDto(PlayerDto playerDto) {
//        this.dto = playerDto;
    }
}

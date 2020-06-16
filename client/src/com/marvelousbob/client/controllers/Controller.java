package com.marvelousbob.client.controllers;

import static com.marvelousbob.client.MyGame.client;

import com.marvelousbob.client.entities.Player;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Controller {

    @Getter
    private PlayerDto selfPlayerDto;
    @Getter
    private Player selfPlayer;

    public Controller(PlayerDto selfPlayerDto) {
        this.selfPlayerDto = selfPlayerDto;
    }

    public void playerTapped(float x, float y) {
        log.debug("Tapped on (%f,%f)".formatted(x, y));
        float destX = x - selfPlayerDto.getSize() / 2;
        float destY = y - selfPlayerDto.getSize() / 2;

        var moveActionDto = new MoveActionDto();
        moveActionDto.setDestX(destX);
        moveActionDto.setDestY(destY);
        moveActionDto.setPlayerId(selfPlayerDto.getUuid());
        log.debug("sending MoveActionDto: " + moveActionDto);
        client.getClient().sendTCP(moveActionDto);
    }
}

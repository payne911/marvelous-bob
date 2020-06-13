package com.marvelousbob.client.controllers;

import com.marvelousbob.common.network.register.dto.PlayerDto;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Controller {

    private PlayerDto selfPlayerDto;

    public Controller(PlayerDto selfPlayerDto) {
        this.selfPlayerDto = selfPlayerDto;
    }

    public void playerTapped(float x, float y) {
        log.debug("Tapped on (%f,%f)".formatted(x, y));
        selfPlayerDto.setDestX(x - selfPlayerDto.getSize() / 2);
        selfPlayerDto.setDestY(y - selfPlayerDto.getSize() / 2);
    }
}

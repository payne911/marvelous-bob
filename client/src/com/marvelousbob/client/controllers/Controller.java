package com.marvelousbob.client.controllers;

import lombok.extern.slf4j.Slf4j;

import static com.marvelousbob.client.MyGame.selfPlayer;

@Slf4j
public class Controller {

    public void playerTapped(float x, float y) {
        log.debug("Tapped on (%f,%f)".formatted(x, y));
        selfPlayer.setDestX(x - selfPlayer.getSize() / 2);
        selfPlayer.setDestY(y - selfPlayer.getSize() / 2);
    }
}

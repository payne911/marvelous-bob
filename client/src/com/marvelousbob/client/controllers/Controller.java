package com.marvelousbob.client.controllers;

import com.marvelousbob.client.MyGame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Controller {

    public void playerTapped(float x, float y) {
        log.debug("Tapped on (%f,%f)".formatted(x, y));
        MyGame.selfPlayer.setDestX(x);
        MyGame.selfPlayer.setDestY(y);
    }
}

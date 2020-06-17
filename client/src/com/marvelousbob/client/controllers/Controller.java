package com.marvelousbob.client.controllers;

import static com.marvelousbob.client.MyGame.client;

import com.marvelousbob.client.entities.Player;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.state.StateRecords;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Controller {

    @Getter
    private final PlayerDto selfPlayerDto;
    @Getter
    private final Player selfPlayer;
    @Getter
    private final StateRecords stateRecords;

    public Controller(PlayerDto selfPlayerDto) {
        this.selfPlayerDto = selfPlayerDto;
        this.selfPlayer = new Player(selfPlayerDto);
        this.stateRecords = new StateRecords();
    }

    public void playerTapped(float x, float y) {
        log.debug("Tapped on (%f,%f)".formatted(x, y));
        float destX = x - selfPlayer.getSize() / 2;
        float destY = y - selfPlayer.getSize() / 2;

        var moveActionDto = new MoveActionDto();
        moveActionDto.setDestX(destX);
        moveActionDto.setDestY(destY);
        moveActionDto.setPlayerId(selfPlayer.getUuid());
        log.debug("sending MoveActionDto: " + moveActionDto);
        client.getClient().sendTCP(moveActionDto);

        stateRecords.addLocalRecord(new GameStateDto());
    }
}

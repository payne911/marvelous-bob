package com.marvelousbob.client.controllers;

import static com.marvelousbob.client.MyGame.client;
import static com.marvelousbob.client.MyGame.controller;

import com.esotericsoftware.kryonet.Client;
import com.marvelousbob.client.entities.Player;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.state.GameStateRecords;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Controller {

    /**
     * This points to the {@link PlayerDto} of the player which is in the {@link #getLocalState()
     * GameStateUpdater's mutableCurrentLocalGameState} which itself is also {@code final}.
     */
    @Getter
    private final PlayerDto selfPlayerDto;

    /**
     * The {@link PlayerDto} wrapper which contains the necessary information for scene2d to draw.
     */
    @Getter
    private final Player selfPlayer;

    /**
     * Takes care of everything that relates to the Game State logic.
     */
    @Getter
    private final GameStateUpdater gameStateUpdater;

    /**
     * Used for the TCP
     */
    @Getter
    private final Client kryoClient;

    public Controller(PlayerDto selfPlayerDto, Client kryoClient, GameStateDto initialGameState) {
        this.selfPlayerDto = selfPlayerDto;
        this.kryoClient = kryoClient;
        this.selfPlayer = new Player(selfPlayerDto);
        this.gameStateUpdater =
                new GameStateUpdater(new GameStateRecords(), kryoClient.getKryo(),
                        initialGameState);
    }

    public void playerTapped(float x, float y) {
        log.debug("Tapped on (%f,%f)".formatted(x, y));
        log.debug("Before changes, game state is: " + controller.getLocalState());

        /* Adjust input coordinate to center of player. */
        float destX = x - selfPlayerDto.getSize() / 2;
        float destY = y - selfPlayerDto.getSize() / 2;
        log.debug("Adjusted for center of player from (%f,%f) to (%f,%f)"
                .formatted(x, y, destX, destY));

        /* Assume local input will be accepted by server. */
        selfPlayerDto.setDestX(destX);
        selfPlayerDto.setDestY(destY);

        /* Send movement request to server. */
        var moveActionDto = new MoveActionDto();
        moveActionDto.setDestX(destX);
        moveActionDto.setDestY(destY);
        moveActionDto.setPlayerId(selfPlayerDto.getUuid());
        moveActionDto.stampNow();
        log.debug("sending MoveActionDto: " + moveActionDto);
        client.getClient().sendTCP(moveActionDto);
        log.debug("After changes, game state is: " + controller.getLocalState());

        /* Make sure to record this local state for future reconciliation. */
        registerCurrentLocalState();
    }

    /**
     * Adds a snapshot of the current state of the game into the {@link GameStateRecords}.
     */
    public void registerCurrentLocalState() {
        gameStateUpdater.registerCurrentLocalState();
    }

    /**
     * @return the {@code mutableCurrentLocalGameState} of the {@link GameStateUpdater}.
     */
    public GameStateDto getLocalState() {
        return gameStateUpdater.getMutableCurrentLocalGameState();
    }
}

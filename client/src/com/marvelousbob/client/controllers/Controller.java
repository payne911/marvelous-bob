package com.marvelousbob.client.controllers;

import static com.marvelousbob.client.MyGame.client;
import static com.marvelousbob.client.MyGame.controller;

import com.esotericsoftware.kryonet.Client;
import com.marvelousbob.client.entities.MeleePlayer;
import com.marvelousbob.client.entities.Player;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.IndexedMoveActionDto;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.state.GameStateRecords;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Controller {


    /**
     * The {@link PlayerDto} wrapper which contains the necessary information for scene2d to draw.
     * <p>
     * To update the Dto, use {@link Player#updateFromDto(PlayerDto)}.
     */
    @Getter
    private final Player selfPlayer;

    /**
     * Takes care of everything that relates to the Game State logic.
     */
    @Getter
    private final GameStateUpdater gameStateUpdater;

    /**
     * Used for the TCP network communications with the server.
     */
    @Getter
    private final Client kryoClient;


    private long moveIndex;


    public Controller(Client kryoClient, GameStateDto initialGameState) {
        this.kryoClient = kryoClient;
        this.gameStateUpdater =
                new GameStateUpdater(new GameStateRecords(), kryoClient.getKryo(),
                        initialGameState);
        this.selfPlayer = new MeleePlayer(getSelfPlayerDto());
        this.moveIndex = 0;
    }


    public void playerClicked(float x, float y) {
        log.debug("Tapped on (%f,%f)".formatted(x, y));
        log.debug("Before changes, game state is: " + controller.getLocalState());

        PlayerDto self = getSelfPlayerDto();

        /* Adjust input coordinate to center of player. */
        float destX = x - self.getSize() / 2;
        float destY = y - self.getSize() / 2;
        log.debug("Adjusted for center of player from (%f,%f) to (%f,%f)"
                .formatted(x, y, destX, destY));

        /* Assume local input will be accepted by server. */
        self.setDestX(destX);
        self.setDestY(destY);

        /* Send movement request to server. */
        var moveActionDto = new MoveActionDto();
        moveActionDto.setDestX(destX);
        moveActionDto.setDestY(destY);
        moveActionDto.setPlayerId(self.getUuid());
        moveActionDto.stampNow();
        log.debug("sending MoveActionDto: " + moveActionDto);
        IndexedMoveActionDto moveDTO = new IndexedMoveActionDto(moveActionDto, moveIndex++);
        gameStateUpdater.addLocalProcessedInput(moveDTO);
        client.getClient().sendTCP(moveDTO);
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

    public PlayerDto getSelfPlayerDto() {
        return gameStateUpdater.getMutableCurrentLocalGameState()
                .getPlayer(selfPlayer.getUuid())
                .orElseThrow(() -> new MarvelousBobException(
                        "Illegal State: could not find the PlayerDto associated with yourself (your client)."));
    }
}

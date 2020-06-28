package com.marvelousbob.client.controllers;

import static com.marvelousbob.client.MyGame.client;
import static com.marvelousbob.client.MyGame.controller;

import com.esotericsoftware.kryonet.Client;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.model.entities.GameWorld;
import com.marvelousbob.common.model.entities.MeleePlayer;
import com.marvelousbob.common.model.entities.Player;
import com.marvelousbob.common.network.register.dto.IndexedMoveActionDto;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.state.GameStateUpdater;
import com.marvelousbob.common.state.LocalGameState;
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


    public Controller(Client kryoClient, GameWorld initGameWorld, PlayerDto initPlayerDto) {
        this.kryoClient = kryoClient;
        this.gameStateUpdater = new GameStateUpdater(kryoClient.getKryo(), initGameWorld);
        this.selfPlayer = new MeleePlayer(initPlayerDto);
        this.moveIndex = 0;
    }

    public void updateGameState(float delta) {
        gameStateUpdater.updateGameState(delta);
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
        moveActionDto.setSourcePlayerUuid(self.getUuid());
        moveActionDto.stampNow();
        IndexedMoveActionDto moveDTO = new IndexedMoveActionDto(moveActionDto, moveIndex++);
        log.debug("sending IndexedMoveActionDto: " + moveDTO);
        client.getClient().sendTCP(moveDTO);
        log.debug("After changes, game state is: " + controller.getLocalState());
    }

    /**
     * @return the {@code mutableCurrentLocalGameState} of the {@link GameStateUpdater}.
     */
    public LocalGameState getLocalState() {
        return gameStateUpdater.getMutableLocalGameState();
    }

    public PlayerDto getSelfPlayerDto() {
        return gameStateUpdater.getMutableLocalGameState()
                .getPlayer(selfPlayer.getUuid())
                .orElseThrow(() -> new MarvelousBobException(
                        "Illegal State: could not find the PlayerDto associated with yourself (your client)."));
    }

    public GameWorld getGameWorld() {
        return gameStateUpdater.getMutableGameWorld();
    }
}

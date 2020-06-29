package com.marvelousbob.client.controllers;

import static com.marvelousbob.client.MyGame.client;
import static com.marvelousbob.client.MyGame.controller;

import com.esotericsoftware.kryonet.Client;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.model.entities.GameWorld;
import com.marvelousbob.common.model.entities.Player;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.state.GameWorldManager;
import com.marvelousbob.common.state.LocalGameState;
import com.marvelousbob.common.utils.UUID;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@ToString(callSuper = true)
@Slf4j
public class Controller {

    @Getter
    private final UUID selfPlayerUuid;

    /**
     * Takes care of everything that relates to the Game State logic.
     */
    @Getter
    private final GameWorldManager gameWorldManager;

    /**
     * Used for the TCP network communications with the server.
     */
    @Getter
    private final Client kryoClient;


    public Controller(Client kryoClient, GameWorld initGameWorld, UUID selfPlayerUuid) {
        this.kryoClient = kryoClient;
        this.gameWorldManager = new GameWorldManager(initGameWorld);
        this.selfPlayerUuid = selfPlayerUuid;
    }

    public void updateGameState(float delta) {
        gameWorldManager.updateGameState(delta);
    }

    public void playerClicked(float destX, float destY) {
        log.debug("Tapped on (%f,%f)".formatted(destX, destY));
        log.debug("Before changes, game state is: " + controller.getLocalState());

        Player self = getSelfPlayer();

        /* Assume local input will be accepted by server. */
        self.getDestination().x = destX;
        self.getDestination().y = destY;

        /* Send movement request to server. */
        var moveActionDto = new MoveActionDto();
        moveActionDto.setDestX(destX);
        moveActionDto.setDestY(destY);
        moveActionDto.setSourcePlayerUuid(self.getUuid());
        moveActionDto.stampNow();
        moveActionDto.setSourcePlayerUuid(selfPlayerUuid);
        log.debug("sending MoveActionDto: " + moveActionDto);
        client.getClient().sendTCP(moveActionDto);
        log.debug("After changes, game state is: " + controller.getLocalState());
    }


    public GameWorld getGameWorld() {
        return gameWorldManager.getMutableGameWorld();
    }

    public LocalGameState getLocalState() {
        return getGameWorld().getLocalGameState();
    }

    public Player getSelfPlayer() {
        return getLocalState().getPlayer(selfPlayerUuid)
                .orElseThrow(() -> new MarvelousBobException(
                        "Illegal State: could not find the PlayerDto associated with yourself (your client)."));
    }
}

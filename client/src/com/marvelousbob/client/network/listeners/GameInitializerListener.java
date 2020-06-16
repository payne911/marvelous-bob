package com.marvelousbob.client.network.listeners;

import static com.marvelousbob.client.MyGame.controller;
import static com.marvelousbob.client.MyGame.gameStateDto;
import static com.marvelousbob.client.MyGame.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.GameScreen;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.client.inputProcessors.MyGestureListener;
import com.marvelousbob.client.inputProcessors.MyInputProcessor;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameInitialization;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.network.register.dto.UUID;
import java.util.Objects;


public class GameInitializerListener extends AbstractListener<GameInitialization> {

    private final Game marvelousBob;

    public GameInitializerListener(Game marvelousBob) {
        super(GameInitialization.class);
        this.marvelousBob = marvelousBob;
    }

    @Override
    public void accept(Connection connection, GameInitialization gameInitialization) {
        // if the game is not initialized yet
        UUID currentPlayerUuid = gameInitialization.getCurrentPlayerId();
        PlayerDto selfPlayerDto = null;
        for (PlayerDto p : gameInitialization.getGameStateDto().getPlayerDtos()) {
            if (p.isEquals(currentPlayerUuid)) {
                selfPlayerDto = p;
                break;
            }
        }

        if (Objects.isNull(selfPlayerDto) || Objects.isNull(selfPlayerDto.getUuid())) {
            throw new IllegalStateException(
                    "Server did not send a valid GameState (it does not contain the new Player or he is labeled with the wrong ID).");
        }
        controller = new Controller(selfPlayerDto);
        gameStateDto = gameInitialization.getGameStateDto();

        // processors
        MyGestureListener inputProcessor1 = new MyGestureListener(stage.getCamera(), controller);
        MyInputProcessor inputProcessor2 = new MyInputProcessor(stage.getCamera(), controller);
        Gdx.input.setInputProcessor(
                new InputMultiplexer(stage, new GestureDetector(inputProcessor1), inputProcessor2));

        // draw screen
        marvelousBob.setScreen(new GameScreen());
    }
}

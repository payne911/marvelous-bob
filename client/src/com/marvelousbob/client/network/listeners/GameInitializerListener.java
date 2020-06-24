package com.marvelousbob.client.network.listeners;

import static com.marvelousbob.client.MyGame.controller;
import static com.marvelousbob.client.MyGame.selfColorIndex;
import static com.marvelousbob.client.MyGame.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.client.entities.GameWorld;
import com.marvelousbob.client.inputProcessors.MyGestureListener;
import com.marvelousbob.client.inputProcessors.MyInputProcessor;
import com.marvelousbob.client.screens.GameScreen;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameInitializationDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;


/**
 * Used to initialize the game. Lots of variables being instantiated here.
 * <p>
 * The {@link GameScreen} is never shown before this class completes the initialization.
 */
@Slf4j
public class GameInitializerListener extends AbstractListener<GameInitializationDto> {

    /**
     * Used to call {@link Game#setScreen(Screen)}.
     */
    private final Game marvelousBob;

    /**
     * Used by the {@link com.marvelousbob.client.controllers.GameStateUpdater} to do deep copies.
     */
    private final Client kryoClient;

    public GameInitializerListener(Game marvelousBob, Client kryoClient) {
        super(GameInitializationDto.class);
        this.marvelousBob = marvelousBob;
        this.kryoClient = kryoClient;
    }

    @Override
    public void accept(Connection connection, GameInitializationDto gameInit) {
        if (Objects.isNull(gameInit)) {
            throw new IllegalStateException("Server did not send a valid GameState.");
        }
        log.debug("Received initial GS: " + gameInit);
        Optional<PlayerDto> selfPlayerDto = gameInit.getGameStateDto()
                .getPlayer(gameInit.getCurrentPlayerId());

        if (selfPlayerDto.isEmpty() || Objects.isNull(selfPlayerDto.get().getUuid())) {
            throw new IllegalStateException(
                    "Server did not send a valid GameState (it does not contain the new Player or he is labeled with the wrong ID).");
        }
        selfColorIndex = selfPlayerDto.get().getColorIndex();
        controller = new Controller(kryoClient, gameInit.getGameStateDto());

        // processors
        MyGestureListener inputProcessor1 = new MyGestureListener(stage.getCamera(), controller);
        MyInputProcessor inputProcessor2 = new MyInputProcessor(stage.getCamera(), controller);
        Gdx.input.setInputProcessor(
                new InputMultiplexer(stage, new GestureDetector(inputProcessor1), inputProcessor2));

        // draw screen
        Gdx.app.postRunnable(() -> marvelousBob.setScreen(new GameScreen(new GameWorld())));
    }
}

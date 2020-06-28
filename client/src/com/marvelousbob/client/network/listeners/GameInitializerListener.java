package com.marvelousbob.client.network.listeners;

import static com.marvelousbob.client.MyGame.controller;
import static com.marvelousbob.client.MyGame.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.MarvelousBob;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.client.inputProcessors.MyGestureListener;
import com.marvelousbob.client.inputProcessors.MyInputProcessor;
import com.marvelousbob.client.screens.GameScreen;
import com.marvelousbob.common.mapper.GameStateMapper;
import com.marvelousbob.common.mapper.LevelMapper;
import com.marvelousbob.common.model.entities.GameWorld;
import com.marvelousbob.common.model.entities.Level;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameInitializationDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.state.GameStateUpdater;
import com.marvelousbob.common.state.LocalGameState;
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
    private final MarvelousBob marvelousBob;
    private final LevelMapper levelMapper;
    private final GameStateMapper gameStateMapper;

    /**
     * Used by the {@link GameStateUpdater} to do deep copies.
     */
    private final Client kryoClient;

    public GameInitializerListener(MarvelousBob marvelousBob, Client kryoClient) {
        super(GameInitializationDto.class);
        this.marvelousBob = marvelousBob;
        this.kryoClient = kryoClient;
        this.levelMapper = new LevelMapper();
        this.gameStateMapper = new GameStateMapper();
    }

    @Override
    public void accept(Connection connection, GameInitializationDto gameInit) {
        if (Objects.isNull(gameInit)) {
            throw new IllegalStateException("Server did not send a valid GameState.");
        }
        log.debug("Received initial GS: " + gameInit);
        Optional<PlayerDto> selfPlayerDto = gameInit.getFirstGameStateDto()
                .getPlayer(gameInit.getCurrentPlayerId());

        if (selfPlayerDto.isEmpty() || Objects.isNull(selfPlayerDto.get().getUuid())) {
            throw new IllegalStateException(
                    "Server did not send a valid GameState (it does not contain the new Player or he is labeled with the wrong ID).");
        }

        /* Input processors. */
        MyGestureListener inputProcessor1 = new MyGestureListener(stage.getCamera(), controller);
        MyInputProcessor inputProcessor2 = new MyInputProcessor(stage.getCamera(), controller);
        Gdx.input.setInputProcessor(
                new InputMultiplexer(stage, new GestureDetector(inputProcessor1), inputProcessor2));

        logicAfterPriorChecks(gameInit, selfPlayerDto.get());
    }

    /**
     * After all the validation is done, this is called.
     */
    private void logicAfterPriorChecks(GameInitializationDto gameInit, PlayerDto selfPlayerDto) {
        GameWorld gameWorld = new GameWorld();
        gameWorld.setLevel(extractInitialLevel(gameInit));
        gameWorld.setLocalGameState(extractInitialLocalGameState(gameInit));

        controller = new Controller(kryoClient, gameWorld, selfPlayerDto);

        /* Draw the screen to start the game. */
        Gdx.app.postRunnable(() -> marvelousBob.setScreen(new GameScreen(controller)));
    }

    private LocalGameState extractInitialLocalGameState(GameInitializationDto gameInitDto) {
        return gameStateMapper.fromGameStateDto(gameInitDto);
    }

    private Level extractInitialLevel(GameInitializationDto gameInitDto) {
        return levelMapper.toLevel(gameInitDto.getCurrentLevel());
    }
}

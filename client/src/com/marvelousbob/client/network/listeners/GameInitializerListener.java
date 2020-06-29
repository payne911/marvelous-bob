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
import com.marvelousbob.common.model.entities.GameWorld;
import com.marvelousbob.common.model.entities.dynamic.Player;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameInitializationDto;
import com.marvelousbob.common.state.GameWorldManager;
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

    /**
     * Used by the {@link GameWorldManager} to do deep copies.
     */
    private final Client kClient;

    public GameInitializerListener(MarvelousBob marvelousBob, Client kryoClient) {
        super(GameInitializationDto.class);
        this.marvelousBob = marvelousBob;
        this.kClient = kryoClient;
    }

    @Override
    public void accept(Connection connection, GameInitializationDto gameInit) {
        if (Objects.isNull(gameInit)) {
            throw new IllegalStateException("Server did not send a valid GameState.");
        }
        log.debug("Received initial GS: " + gameInit);
        Optional<Player> selfPlayer = gameInit.newGameWorld
                .getLocalGameState().getPlayer(gameInit.getCurrentPlayerId());

        if (selfPlayer.isEmpty() || Objects.isNull(selfPlayer.get().getUuid())) {
            throw new IllegalStateException(
                    "Server did not send a valid GameState (it does not contain the new Player or he is labeled with the wrong ID).");
        }

        GameWorld gameWorld = gameInit.newGameWorld;
        log.info("before controller");
        controller = new Controller(kClient, gameWorld, gameInit.currentPlayerId);
        log.info("controller: " + controller);

        /* Input processors. */
        MyGestureListener inputProcessor1 = new MyGestureListener(stage.getCamera(), controller);
        MyInputProcessor inputProcessor2 = new MyInputProcessor(stage.getCamera(), controller);
        Gdx.input.setInputProcessor(
                new InputMultiplexer(stage, new GestureDetector(inputProcessor1), inputProcessor2));

        /* Draw the screen to start the game. */
        Gdx.app.postRunnable(() -> {
            marvelousBob.setScreen(new GameScreen(controller));

            LocalGameState localGameState = controller.getGameWorld().getLocalGameState();

            /* Listeners which require the GameScreen to have been initialized. */
            kClient.addListener(new GameStateListener());
//            kClient.addListener(new LagListener(0, 0, new GameStateListener()));
            kClient.addListener(new MoveActionListener(localGameState, gameInit.currentPlayerId));
            kClient.addListener(new NewGameWorldListener(controller.getGameWorldManager()));
            kClient.addListener(new NewPlayerListener(localGameState, gameInit.currentPlayerId));
            kClient.addListener(new PlayerDisconnectListener(localGameState));
        });
    }
}

package com.marvelousbob.client.network.listeners;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.GameScreen;
import com.marvelousbob.client.MyGame;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.client.inputProcessors.MyGestureListener;
import com.marvelousbob.client.inputProcessors.MyInputProcessor;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameIntialization;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.network.register.dto.UUID;

import static com.marvelousbob.client.MyGame.controller;
import static com.marvelousbob.client.MyGame.stage;


public class GameInitializerListener extends AbstractListener<GameIntialization> {

    private final Game gdxGame;

    public GameInitializerListener(Game gdxGame) {
        super(GameIntialization.class);
        this.gdxGame = gdxGame;
    }

    @Override
    public void accept(Connection connection, GameIntialization game) {
        // if the game is not initialized yet
        UUID currentPlayerUuid = game.getCurrentPlayerId();
        if (controller == null) {
            PlayerDto self = null;
            for (PlayerDto p : game.getGameStateDto().getPlayerDtos()) {
                if (p.isEqulas(currentPlayerUuid)) {
                    self = p;
                }
            }
            if (self != null) {
                controller = new Controller(self);
            }
        }

        // update current game state
        MyGame.gameStateDto = game.getGameStateDto();

        // processors
        MyInputProcessor inputProcessor1 = new MyInputProcessor(stage.getCamera(), controller);
        MyGestureListener inputProcessor2 = new MyGestureListener(stage.getCamera(), controller);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, inputProcessor1, new GestureDetector(inputProcessor2)));

        // draw screen
        gdxGame.setScreen(new GameScreen());
    }
}

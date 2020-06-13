package com.marvelousbob.client.network;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.marvelousbob.client.GameScreen;
import com.marvelousbob.client.MyGame;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.client.inputProcessors.MyGestureListener;
import com.marvelousbob.client.inputProcessors.MyInputProcessor;
import com.marvelousbob.common.network.register.dto.*;

import static com.marvelousbob.client.MyGame.controller;
import static com.marvelousbob.client.MyGame.stage;


/**
 * The callbacks to be used on different events from the network:
 * { received, connected, disconnected, idle }
 */
public class ClientListener implements Listener {

    private final Game gdxGame;

    public ClientListener(Game gdxGame) {
        this.gdxGame = gdxGame;
    }

    @Override
    public void received(Connection connection, Object receivedObject) {
        if (receivedObject instanceof Msg msg) onMsg(msg);
        if (receivedObject instanceof Ping ping) onPing(ping);
        if (receivedObject instanceof GameIntialization initialization) onGameInitialized(initialization);
    }

    private void onGameInitialized(GameIntialization game) {
        // if the game is not initialized yet
        UUID currentPlayerUuid = game.getCurrentPlayerId();
        if (MyGame.controller == null) {
            PlayerDto self = null;
            for (PlayerDto p : game.getGameState().getPlayerDtos()) {
                if (p.isEqulas(currentPlayerUuid)) {
                    self = p;
                }
            }
            if (self != null) {
                MyGame.controller = new Controller(self);
            }
        }

        // update current game state
        MyGame.gameState = game.getGameState();

        MyInputProcessor inputProcessor1 = new MyInputProcessor(stage.getCamera(),
                controller);
        MyGestureListener inputProcessor2 = new MyGestureListener(stage.getCamera(),
                controller);
        Gdx.input.setInputProcessor(new InputMultiplexer(
                stage,
                inputProcessor1,
                new GestureDetector(inputProcessor2)));

        gdxGame.setScreen(new GameScreen());
    }


    private void onPing(Ping ping) {
        MyGame.client.getLatencyReport().addToRunningAverage(ping.getTimestamp());
    }

    private void onMsg(Msg msg) {
        System.out.println(msg);
    }
}

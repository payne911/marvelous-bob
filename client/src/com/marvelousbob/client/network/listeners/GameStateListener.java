package com.marvelousbob.client.network.listeners;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.MyGame;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.GameStateDto;


public class GameStateListener extends AbstractListener<GameStateDto> {

    private Game game;

    public GameStateListener(Game game) {
        super(GameStateDto.class);
        this.game = game;
    }

    @Override
    public void accept(Connection connection, GameStateDto gameStateDto) {
        //TODO: 2020-06-13 Better game state update
        // --- OLA
        MyGame.gameStateDto = gameStateDto;
    }
}

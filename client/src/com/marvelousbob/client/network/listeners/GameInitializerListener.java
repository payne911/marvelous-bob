package com.marvelousbob.client.network.listeners;

import com.marvelousbob.client.MyGame;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.common.network.register.dto.GameIntialization;
import com.marvelousbob.common.network.register.dto.GameState;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.network.register.dto.UUID;


public class GameInitializerListener extends AbstractListener<GameIntialization> {

    public GameInitializerListener() {
        super(GameIntialization.class);
    }

    @Override
    public void accept(GameIntialization game) {
        // if the game is not initialized yet
        UUID currentPLayerUuid = game.getCurrentPlayerId();
        if (MyGame.controller == null) {
            PlayerDto self = null;
            for (PlayerDto p : game.getGameState().getPlayerDtos()) {
                if (p.isEqulas(currentPLayerUuid)) {
                    self = p;
                }
            }
            if (self != null) {
                MyGame.controller = new Controller(self);
            }
        }

        // update current game state
        GameState gameState = new GameState();
        gameState.stampNow();
        gameState.setPlayerDtos(game.getGameState().getPlayerDtos());
        MyGame.gameState = gameState;
    }
}

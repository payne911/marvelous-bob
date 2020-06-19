package com.marvelousbob.server;

import com.badlogic.gdx.ScreenAdapter;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.utils.MovementUtils;
import com.marvelousbob.server.listeners.DebugListener;
import com.marvelousbob.server.listeners.MoveActionListener;
import com.marvelousbob.server.listeners.PlayerConnectionListener;
import com.marvelousbob.server.listeners.PlayerDisconnectionListener;
import lombok.SneakyThrows;

public class BobServerScreen extends ScreenAdapter {

    private final Server server;
    private final GameStateDto gameStateDto;

    public BobServerScreen(Server server) {
        this.server = server;
        this.gameStateDto = new GameStateDto();
    }

    @Override
    public void show() {
        gameStateDto.stampNow();
        server.addListener(new DebugListener(server, gameStateDto));
        server.addListener(new PlayerConnectionListener(server, gameStateDto));
        server.addListener(new MoveActionListener(gameStateDto));
        server.addListener(new PlayerDisconnectionListener(gameStateDto));
    }

    @Override
    @SneakyThrows
    public void render(float delta) {
        boolean hasMoved = MovementUtils.interpolatePlayers(gameStateDto, delta);
        if (hasMoved) {
            gameStateDto.stampNow();
            server.sendToAllTCP(gameStateDto);
        }
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

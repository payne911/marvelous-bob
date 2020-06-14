package com.marvelousbob.server;

import com.badlogic.gdx.ScreenAdapter;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.utils.MovementUtils;
import com.marvelousbob.server.listeners.MoveActionListener;
import com.marvelousbob.server.listeners.PlayerConnectionListener;
import com.marvelousbob.server.listeners.ServerListener;
import lombok.SneakyThrows;

public class BobServerScreen extends ScreenAdapter {

    private Server server;
    private GameStateDto gameStateDto;

    public BobServerScreen(Server server) {
        this.server = server;
    }

    @Override
    public void show() {
        gameStateDto = new GameStateDto();
        gameStateDto.stampNow();
        server.addListener(new ServerListener(server, gameStateDto));
        server.addListener(new PlayerConnectionListener(server, gameStateDto));
        server.addListener(new MoveActionListener(gameStateDto));
    }

    @Override
    @SneakyThrows
    public void render(float delta) {
        MovementUtils.interpolatePlayers(gameStateDto, delta);
        server.sendToAllTCP(gameStateDto);
    }
}

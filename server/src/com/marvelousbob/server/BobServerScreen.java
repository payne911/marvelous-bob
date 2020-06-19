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
    private final GameStateDto mutableGameState;

    public BobServerScreen(Server server) {
        this.server = server;
        this.mutableGameState = new GameStateDto();
    }

    @Override
    public void show() {
        mutableGameState.stampNow();
        server.addListener(new DebugListener(server, mutableGameState));
        server.addListener(new PlayerConnectionListener(server, mutableGameState));
        server.addListener(new MoveActionListener(mutableGameState));
        server.addListener(new PlayerDisconnectionListener(mutableGameState));
    }

    @Override
    @SneakyThrows
    public void render(float delta) {
        boolean hasMoved = MovementUtils.interpolatePlayers(mutableGameState, delta);
        if (hasMoved) {
            mutableGameState.stampNow();
            server.sendToAllTCP(mutableGameState);
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

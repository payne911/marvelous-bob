package com.marvelousbob.server;

import com.badlogic.gdx.ScreenAdapter;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.server.listeners.DebugListener;
import com.marvelousbob.server.listeners.EnemyCollisionListener;
import com.marvelousbob.server.listeners.MoveActionListener;
import com.marvelousbob.server.listeners.PlayerConnectionListener;
import com.marvelousbob.server.listeners.PlayerDisconnectionListener;
import com.marvelousbob.server.model.ServerState;
import lombok.SneakyThrows;

public class BobServerScreen extends ScreenAdapter {

    private static final float LOOP_SPEED = 100;

    private final Server server;
    private final ServerState serverState;

    private float deltaAcc;


    public BobServerScreen(Server server) {
        this.server = server;
        this.serverState = new ServerState();
        this.deltaAcc = 0;
    }

    @Override
    public void show() {
        server.addListener(new DebugListener(server));
        server.addListener(new PlayerConnectionListener(server, serverState));
        server.addListener(new MoveActionListener(serverState));
        server.addListener(new PlayerDisconnectionListener(serverState));
        server.addListener(new EnemyCollisionListener(serverState));
    }

    @Override
    @SneakyThrows
    public void render(float delta) {

        if (deltaAcc >= LOOP_SPEED) {
            deltaAcc = 0f; // or subtract the amount of LOOP_SPEED... as we decide
//            serverState.executeAll(deltaAcc);
//            Optional<IndexedGameStateDto> optionalIndexedGameStateDto = serverState.update(deltaAcc);
//            optionalIndexedGameStateDto.ifPresent(server::sendToAllTCP);
//            serverState.reset();
            serverState.runGameLogic(delta);
            GameStateDto gameStateDto = serverState.getCurrentGameStateAsDto();
            server.sendToAllTCP(gameStateDto);
        } else {
            deltaAcc += delta;
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

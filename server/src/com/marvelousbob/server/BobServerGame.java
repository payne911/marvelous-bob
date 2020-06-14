package com.marvelousbob.server;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.constants.NetworkConstants;
import com.marvelousbob.common.network.register.Register;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.server.listeners.PlayerConnectionListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BobServerGame extends Game {

    private final Server server;
    private final GameStateDto gameStateDto;

    @SneakyThrows
    public BobServerGame() {
        this.server = new Server();
        Register.registerClasses(server);
        gameStateDto = new GameStateDto(); // // TODO: 2020-06-13 probably a better way to initialize a new GameState   --- OLA
    }

    @Override
    @SneakyThrows
    public void create() {
        log.info("SERVER STARTING!!!");
        gameStateDto.stampNow();
        server.addListener(new Listener.ThreadedListener(new ServerListener(server, gameStateDto)));
        server.addListener(new Listener.ThreadedListener(new PlayerConnectionListener(server, gameStateDto)));
        server.start();
        server.bind(NetworkConstants.PORT);
        log.info("SERVER STARTED!!!");
    }
}

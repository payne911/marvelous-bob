package com.marvelousbob.server;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.constants.NetworkConstants;
import com.marvelousbob.common.network.register.Register;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BobServerGame extends Game {

    private final Server server;
    private BobServerScreen screen;

    @SneakyThrows
    public BobServerGame() {
        this.server = new Server();
        Register.registerClasses(server);
    }

    @Override
    @SneakyThrows
    public void create() {
        log.info("SERVER STARTING!!!");
        server.bind(NetworkConstants.PORT);
        screen = new BobServerScreen(server);
        server.start();
        log.info("SERVER STARTED!!!");
    }
}

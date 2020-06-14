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
    private final BobServerScreen screen;
    private final Register register;

    @SneakyThrows
    public BobServerGame() {
        this.server = new Server();
        this.register = new Register(server);
        this.screen = new BobServerScreen(server);
    }

    @Override
    @SneakyThrows
    public void create() {
        register.registerClasses();
        log.info("SERVER STARTING!!!");
        server.bind(NetworkConstants.PORT);
        server.start();
        log.info("SERVER STARTED!!!");
        setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        screen.dispose();
    }
}
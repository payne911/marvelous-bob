package com.marvelousbob.server;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.constants.NetworkConstants;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BobServerGame extends Game {

    private final Server server;
    private final BobServerScreen serverScreen;


    @SneakyThrows
    public BobServerGame() {
        this.server = new Server(
                NetworkConstants.WRITE_BUFFER_SIZE, NetworkConstants.OBJECT_BUFFER_SIZE);
        server.getKryo().setRegistrationRequired(false);
        server.getKryo().setWarnUnregisteredClasses(true);
        this.serverScreen = new BobServerScreen(server);
    }

    @Override
    @SneakyThrows
    public void create() {
        log.info("SERVER STARTING!!!");
        server.bind(NetworkConstants.PORT);
        server.start();
        log.info("SERVER STARTED!!!");
        setScreen(serverScreen);
    }

    @Override
    public void dispose() {
        if (Objects.nonNull(getScreen())) {
            getScreen().dispose();
        }
    }
}
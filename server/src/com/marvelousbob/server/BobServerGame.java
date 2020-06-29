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
//    private final Register register; // todo: remove if we don't want it anymore
//    private final Register entityRegister;


    @SneakyThrows
    public BobServerGame() {
        this.server = new Server();
        server.getKryo().setRegistrationRequired(false);
        server.getKryo().setWarnUnregisteredClasses(true);
//        this.register = new Register(server);
        this.serverScreen = new BobServerScreen(server);
//        this.entityRegister = new Register(server, "com.marvelousbob.common.model.entities");
    }

    @Override
    @SneakyThrows
    public void create() {
//        register.registerClasses(Dto.class);
//        entityRegister.registerClasses();
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
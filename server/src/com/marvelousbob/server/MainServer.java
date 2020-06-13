package com.marvelousbob.server;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.constants.NetworkConstants;
import com.marvelousbob.common.network.register.Register;
import com.marvelousbob.common.network.register.dto.GameState;
import lombok.SneakyThrows;

public class MainServer {

    private final Server server;

    private GameState gameState;

    @SneakyThrows
    public MainServer() {
        this.server = new Server();
        Register.registerClasses(server);

        server.addListener(new Listener.ThreadedListener(new ServerListener(server, gameState)));
        server.start();
        server.bind(NetworkConstants.PORT);
    }
}

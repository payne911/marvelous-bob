package com.marvelousbob.client.network;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Client;
import com.marvelousbob.client.network.listeners.DebugListener;
import com.marvelousbob.client.network.listeners.GameInitializerListener;
import com.marvelousbob.client.network.listeners.GameStateListener;
import com.marvelousbob.client.network.test.IncrementalAverage;
import com.marvelousbob.common.network.constants.NetworkConstants;
import com.marvelousbob.common.network.register.Register;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.net.InetAddress;

@Getter
public class MyClient {
    @Setter
    private IncrementalAverage latencyReport;
    private final InetAddress addr;
    private final Client client;
    private final Game marvelousBob;
    private final Register register;

    @SneakyThrows
    public MyClient(boolean isLocalServer, Game marvelousBob) {
        this.client = new Client();
        this.marvelousBob = marvelousBob;
        this.register = new Register(client);
        this.addr = isLocalServer
                ? InetAddress.getLocalHost()
                : InetAddress.getByName(NetworkConstants.REMOTE_SERVER_IP);
        this.latencyReport = new IncrementalAverage();
    }

    @SneakyThrows
    public void connect() {
        register.registerClasses();
        client.addListener(new DebugListener());
        client.addListener(new GameInitializerListener(marvelousBob));
        client.addListener(new GameStateListener());
        client.start();
        client.connect(NetworkConstants.TIMEOUT, addr, NetworkConstants.PORT);
    }
}

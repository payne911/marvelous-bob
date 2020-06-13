package com.marvelousbob.client.network;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Client;
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
    private Game game;

    @SneakyThrows
    public MyClient(boolean isLocalServer, Game game) {
        this.client = new Client();
        this.game = game;
        Register.registerClasses(client);
        this.addr = isLocalServer
                ? InetAddress.getLocalHost()
                : InetAddress.getByName(NetworkConstants.REMOTE_SERVER_IP);
        this.latencyReport = new IncrementalAverage();
    }

    @SneakyThrows
    public void connect() {
//        client.addListener(new Listener.ThreadedListener(new OnReceiveClientListener()));
        client.addListener(new ClientListener(game));
        client.start();
        client.connect(NetworkConstants.TIMEOUT, addr, NetworkConstants.PORT);
    }
}

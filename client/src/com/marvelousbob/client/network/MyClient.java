package com.marvelousbob.client.network;

import com.esotericsoftware.kryonet.Client;
import com.marvelousbob.client.network.test.IncrementalAverage;
import com.marvelousbob.common.network.constants.NetworkConstants;
import com.marvelousbob.common.network.register.Register;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.InetAddress;


public class MyClient {


    @Getter
    private final InetAddress addr;

    @Getter
    private final Client client;

    @Getter
    private IncrementalAverage latencyReport;


    public MyClient() {
        this(false);
    }

    @SneakyThrows
    public MyClient(boolean isLocalServer) {
        this.client = new Client();
        Register.registerClasses(client);
        this.addr = isLocalServer
                ? InetAddress.getLocalHost()
                : InetAddress.getByName(NetworkConstants.REMOTE_SERVER_IP);
        this.latencyReport = new IncrementalAverage();
    }

    @SneakyThrows
    public void connect() {
//        client.addListener(new Listener.ThreadedListener(new OnReceiveClientListener()));
        client.addListener(new OnReceiveClientListener());
        client.start();
        client.connect(NetworkConstants.TIMEOUT, addr, NetworkConstants.PORT);
    }
}

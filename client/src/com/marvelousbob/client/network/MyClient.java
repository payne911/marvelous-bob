package com.marvelousbob.client.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.marvelousbob.client.network.test.IncrementalAverage;
import com.marvelousbob.common.network.constants.NetworkConstants;
import com.marvelousbob.common.network.register.Msg;
import com.marvelousbob.common.network.register.Ping;
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
        Listener onReceiveCallback = new Listener() {
            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof Msg m) {
                    System.out.println(m);
                }
                if (o instanceof Ping p) {
                    latencyReport.addToRunningAverage(p.getTimeStamp());
                }
            }
        };
//        client.addListener(new Listener.ThreadedListener(onReceiveCallback));
        client.addListener(onReceiveCallback);
        client.start();
        client.connect(NetworkConstants.TIMEOUT, addr, NetworkConstants.PORT);
    }
}

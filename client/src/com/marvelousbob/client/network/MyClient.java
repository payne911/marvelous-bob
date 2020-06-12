package com.marvelousbob.client.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.marvelousbob.client.network.test.IncrementalAverage;
import com.marvelousbob.common.register.Msg;
import com.marvelousbob.common.register.Ping;
import com.marvelousbob.common.register.Register;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.InetAddress;


public class MyClient {

    public static final String REMOTE_SERVER = "52.60.181.140";
    public static final int PORT = 80;
    public static final int TIMEOUT = 15000;

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
                : InetAddress.getByName(REMOTE_SERVER);
        this.latencyReport = new IncrementalAverage();
    }

    @SneakyThrows
    public void connect() {
        Listener onReceiveCallback = new Listener() {
            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof Msg) {
                    Msg m = (Msg) o;
                    System.out.println(m);
                }
                if (o instanceof Ping) {
                    Ping p = (Ping) o;
                    latencyReport.addToRunningAverage(p.getTimeStamp());
                }
            }
        };
        client.addListener(new Listener.ThreadedListener(onReceiveCallback));
//        client.addListener(onReceiveCallback);
        client.start();
        client.connect(TIMEOUT, addr, PORT);
    }
}
